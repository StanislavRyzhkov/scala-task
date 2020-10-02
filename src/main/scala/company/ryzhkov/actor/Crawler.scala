package company.ryzhkov.actor

import akka.actor.Actor
import akka.http.scaladsl.model._
import akka.http.scaladsl.{Http, HttpExt}
import akka.pattern.pipe
import akka.stream.{Materializer, SystemMaterializer}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContextExecutor, Future}

object Crawler {
  type Strings      = List[String]
  type ParseResult  = Either[String, String]
  type ParseResults = List[ParseResult]

  sealed trait Reply
  case class Result(errors: Strings, urls: Strings) extends Reply
  case class Error() extends Reply

  case class Query(urls: Strings)
}

class Crawler extends Actor {
  import Crawler._

  implicit val ex: ExecutionContextExecutor = context.dispatcher
  implicit val mat: Materializer            = SystemMaterializer(context.system).materializer

  val http: HttpExt = Http(context.system)

  override def receive: Receive = {
    case Query(urls) =>
      val result = Future
        .sequence(urls.map(handleError))
        .map(_.partition(_.isLeft))
        .map(e => bar(e._1, e._2))

      result.pipeTo(sender())
    case _           =>
      sender() ! Error
  }

  def processUrl(url: String): Future[String] =
    for {
      httpResponse <- http.singleRequest(HttpRequest(uri = url))
      html         <- httpResponse.entity.toStrict(4.seconds).map(_.data.utf8String)
    } yield html

  def parseXml(string: String): String = {
    string.substring(0, 30)
  }

  def handleError(url: String): Future[Either[String, String]] =
    processUrl(url)
      .map(parseXml)
      .map(Right(_))
      .recover { case e => Left(e.getMessage) }

  def bar(tuple: (ParseResults, ParseResults)): Result = {
    val errors  = for (Left(i) <- tuple._1) yield i
    val results = for (Right(i) <- tuple._2) yield i
    Result(errors, results)
  }
}
