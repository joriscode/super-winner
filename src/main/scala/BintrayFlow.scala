import me.tongfei.progressbar._

import akka.NotUsed
import akka.http.scaladsl.model._
import Uri._
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.unmarshalling._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json._

import akka.stream.scaladsl._
import akka.stream.ActorMaterializer
import akka.util.ByteString

import akka.actor.ActorSystem

import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Success, Failure, Try}

import java.io.File

object BintrayFlow extends BintrayProtocol {
  // todo: params
  implicit val system = ActorSystem()
  import system.dispatcher
  implicit val materializer = ActorMaterializer()

  val bintray = {
    // from bintray-sbt convention
    // cat ~/.bintray/.credentials
    // host = api.bintray.com
    // user = xxxxxxxxxx
    // password = xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    val home = System.getProperty("user.home")
    val path = home + "/.bintray/.credentials2"
    val nl = System.lineSeparator
    val source = io.Source.fromFile(path)

    val info =
      source.mkString.split(nl).map{ v =>
        val (l, r) = v.span(_ != '=' )
        (l.trim, r.drop(2).trim)
      }.toMap
    source.close()

    info
  }

  def withAuthorization(request: HttpRequest) = {
    (bintray.get("user"), bintray.get("password")) match {
      case (Some(user), Some(key)) => request.withHeaders(Authorization(BasicHttpCredentials(user, key)))
      case _ => request
    }
  }

  // we do not expect Authorization to change the response
  def cachedWithoutAuthorization(request: HttpRequest) =
    (request, request.copy(headers = request.headers.filterNot{ case Authorization(_) => true}))

  val bintrayHttpFlow = Http().cachedHostConnectionPoolHttps[HttpRequest]("bintray.com")
  
  val startQuery = "start_pos"
  def search(start: Int) = {
    // todo: config
    val scalaVersion = "2.11"

    HttpRequest(uri = Uri("https://bintray.com/api/v1/search/file").withQuery(
      Query("name" -> s"*_$scalaVersion*.pom", startQuery -> start.toString)
    ))
  }

  // insannely mutable
  val progress = new ProgressBar("List POMs", 0)

  // Find the pom total count
  val search0 = {
    val totalHeader = "X-RangeLimit-Total"
    Http().singleRequest(search(0)).map(
      _.headers.find(_.name == totalHeader).map(_.value.toInt).getOrElse(0)
    )
  }
  
  val perRequest = 50
  val searchRequests =
    Source.fromFuture(search0).flatMapConcat{totalPoms =>
      progress.start()
      progress.maxHint(totalPoms)
      
      val requestCount = Math.floor(totalPoms.toDouble / perRequest.toDouble).toInt
      Source((0 to requestCount).map(i => 
        cachedWithoutAuthorization(withAuthorization(search(i * 50)))
      ))
    }

  // https pipeline & json extraction
  val listPoms =
    searchRequests
      .via(bintrayHttpFlow)
      .mapAsync(1){
        case (Success(response), request) => {
          progress.stepBy(perRequest)
          Unmarshal(response).to[List[BintraySearch]].map(
            _.map(_.copy(at = request.uri.query().get(startQuery).map(_.toInt)))
          ) recover {
            case Unmarshaller.UnsupportedContentTypeException(_) => {
              // we will get some 500
              println(request)
              println(response)
              List()
            }
          }
        }
        case (Failure(e), _) => Future.failed(e)
      }.mapConcat(identity)

  val listPomsCheckpoint = 
    Flow[BintraySearch]
      .map(_.toJson.compactPrint)
      .map(s => ByteString(s + "\n"))
      .toMat(FileIO.toFile(new File("bintray.json")))(Keep.right)



  def run(): Unit = {
    Await.result(listPoms.alsoTo(listPomsCheckpoint).runForeach(_ => ()), Duration.Inf)
    ()
  }
}