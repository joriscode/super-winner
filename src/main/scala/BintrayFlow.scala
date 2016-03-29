import me.tongfei.progressbar._

import akka.http.scaladsl.model._
import Uri._
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.unmarshalling._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import akka.stream.scaladsl._
import akka.stream.ActorMaterializer

import akka.actor.ActorSystem

import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Success, Failure, Try}

object BintrayFlow extends BintrayProtocol {
  // todo: params
  private implicit val system = ActorSystem()
  import system.dispatcher
  private implicit val materializer = ActorMaterializer()

  private val bintray = {
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

  private def withAuthorization(request: HttpRequest) = {
    (bintray.get("user"), bintray.get("password")) match {
      case (Some(user), Some(key)) => request.withHeaders(Authorization(BasicHttpCredentials(user, key)))
      case _ => request
    }
  }

  // we do not expect Authorization to change the response
  private def cachedWithoutAuthorization(request: HttpRequest): (HttpRequest, HttpRequest) =
    (request, request.copy(headers = request.headers.filterNot{ case Authorization(_) => true}))

  private val bintrayHttpFlow = Http().cachedHostConnectionPoolHttps[HttpRequest]("bintray.com")
  
  private def search(start: Int) = {
    // todo: config
    val scalaVersion = "2.11"

    HttpRequest(uri = Uri("https://bintray.com/api/v1/search/file").withQuery(
      Query("name" -> s"*_$scalaVersion*.pom", "start_pos" -> start.toString)
    ))
  }

  val progress = new ProgressBar("List POMs", 0)

  // Find the pom total count
  private def search0 = {
    val totalHeader = "X-RangeLimit-Total"
    Http().singleRequest(search(0)).map(
      _.headers.find(_.name == totalHeader).map(_.value.toInt).getOrElse(0)
    )
  }
  
  private val perRequest = 50
  private def searchRequests =
    Source.fromFuture(search0).flatMapConcat{totalPoms =>
      progress.start()
      progress.maxHint(totalPoms)
      
      val requestCount = Math.floor(totalPoms.toDouble / perRequest.toDouble).toInt
      Source((0 to requestCount).map(i => 
        cachedWithoutAuthorization(withAuthorization(search(i * 50)))
      ))
    }
  
  // https pipeline & json extraction
  def listPoms =
    searchRequests
      .via(bintrayHttpFlow)
      .mapAsync(1){
        case (Success(r), _) => {
          progress.stepBy(perRequest)
          Unmarshal(r.entity).to[List[BintraySearch]].map(_.toSet)
        }
        case (Failure(e), _) => Future.failed(e)
      }.mapConcat(identity)

  def run(): Unit =
    Await.result(listPoms.runForeach(_ => ()), Duration.Inf)
}