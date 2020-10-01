package company.ryzhkov.controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import spray.json.DefaultJsonProtocol._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class RestController {
  val route: Route = pathPrefix("api" / "create") {
    pathEndOrSingleSlash {
      get {
        complete("Hello")
      }
    }
  }
}
