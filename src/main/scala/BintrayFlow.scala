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
    val path = home + "/.bintray/.credentials"
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

  // Find the pom total count
  private def search0 = {
    val totalHeader = "X-RangeLimit-Total"
    Http().singleRequest(search(0)).map(
      _.headers.find(_.name == totalHeader).map(_.value.toInt).getOrElse(0)
    )
  }
  
  // XXX: logic atMost
  private def searchRequests(atMost: Option[Int]) =
    Source.fromFuture(search0).flatMapConcat{totalPoms =>
      
      val perRequest = 50
      def floor(v: Int) = Math.floor(v.toDouble / perRequest.toDouble).toInt
      val requestCount = floor(totalPoms)
      val atMostTotal = atMost.map(floor)
      val atMostRequestCount = atMost.map(v => Math.min(v, requestCount)).getOrElse(requestCount)

      val out =
        Source((0 to atMostRequestCount).map(i => 
          cachedWithoutAuthorization(withAuthorization(search(i * 50)))
        ))

      atMost.map(c => out.take(c)).getOrElse(out)
    }
  
  // https pipeline & json extraction
  def listPoms(atMost: Option[Int]) =
    searchRequests(atMost)
      .via(bintrayHttpFlow)
      .mapAsync(1){
        case (Success(r), _) => Unmarshal(r.entity).to[List[BintraySearch]].map(_.toSet)
        case (Failure(e), _) => Future.failed(e)
      }.mapConcat(identity)

  // for debug purpose
  def runListPoms(atMost: Option[Int]) = 
    listPoms(atMost).runFold(Set.empty[BintraySearch])((acc, v) => acc + v)
  
}