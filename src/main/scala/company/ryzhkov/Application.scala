package company.ryzhkov

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import company.ryzhkov.components.Env.context

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object Application extends App {
  implicit val system: ActorSystem                        = context.system
  implicit val executionContext: ExecutionContextExecutor = context.executionContext

  val routes = context.restController.route

  val bindingFuture = Http().bindAndHandle(context.restController.route, "localhost", 8080)

  println(s"Server started\nPress RETURN to stop...")
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
}
