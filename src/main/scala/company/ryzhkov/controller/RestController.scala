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
  implicit val timeout: Timeout                     = 15.seconds
  implicit val queryFormat: RootJsonFormat[Query]   = jsonFormat1(Query)
  implicit val resultFormat: RootJsonFormat[Result] = jsonFormat2(Result)
  val crawler: ActorRef                             = system.actorOf(Props[Crawler], "crawler")

  val route: Route = pathPrefix("api" / "titles") {
    pathEndOrSingleSlash {
      post {
        entity(as[Query]) { query =>
          onComplete((crawler ? query).mapTo[Reply]) {
            case Success(value)     =>
              value match {
                case result: Crawler.Result => complete(result)
                case _: Crawler.Error       => complete(400, "Bad request")
              }
            case Failure(exception) => complete(exception.getMessage)
          }
        }
      }
    }
  }
}
