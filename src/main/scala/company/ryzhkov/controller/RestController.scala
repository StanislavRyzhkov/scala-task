package company.ryzhkov.controller

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import company.ryzhkov.actor.Crawler
import company.ryzhkov.actor.Crawler.{Query, Reply, Result}
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.{Failure, Success}

class RestController(system: ActorSystem)(implicit ex: ExecutionContext) {
  implicit val timeout: Timeout                      = 25.seconds
  implicit val messageFormat: RootJsonFormat[Result] = jsonFormat2(Result)
  val crawler: ActorRef                              = system.actorOf(Props[Crawler], "crawler")

  val route: Route = pathPrefix("api" / "create") {
    pathEndOrSingleSlash {
      get {

        val msg =
          (crawler ? Query(
            List(
              "https://www.kommersant.ru/",
              "https://www.geekbrains.ru",
              "https://www.rbc.ru",
              "https://www.twitter.com",
              "https://www.google.com",
              "https://www.yandex.ru",
              "https://www.baeldung.com",
              "https://www.forbes.ru/",
              "123"
            )
          ))
            .mapTo[Reply]

        onComplete(msg) {
          case Success(value)     =>
            value match {
              case r: Crawler.Result => complete(r)
              case _: Crawler.Error  => complete("Bad request")
            }
          case Failure(exception) =>
            complete(exception.getMessage)
        }
      }
    }
  }
}
