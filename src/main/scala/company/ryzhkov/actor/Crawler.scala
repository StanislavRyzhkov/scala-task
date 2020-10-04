package company.ryzhkov.actor

import akka.actor.Actor
import akka.http.scaladsl.model._
import akka.http.scaladsl.{Http, HttpExt}
import akka.pattern.pipe
import akka.stream.{Materializer, SystemMaterializer}
import org.jsoup.Jsoup

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
      val futureTaskList = urls.map(processUrlWithErrorHandle)
      val result         = Future
        .sequence(futureTaskList)
        .map(_.partition(_.isLeft))
        .map(parseResults => splitToResult(parseResults._1, parseResults._2))

      result.pipeTo(sender())
    case _           =>
      sender() ! Error
  }

  private def processUrlToHtml(url: String): Future[String] =
    for {
      httpResponse <- http.singleRequest(HttpRequest(uri = url))
      html         <- httpResponse.entity.toStrict(5.seconds).map(_.data.utf8String)
    } yield html

  private def parseHtml(string: String): String = Jsoup.parse(string).title()

  private def processUrlWithErrorHandle(url: String): Future[ParseResult] =
    processUrlToHtml(url)
      .map(parseHtml)
      .map(Right(_))
      .recover { case e => Left(e.getMessage) }

  private def splitToResult(tuple: (ParseResults, ParseResults)): Result = {
    val errors  = for (Left(i) <- tuple._1) yield i
    val results = for (Right(i) <- tuple._2) yield i
    Result(errors, results)
  }
}
