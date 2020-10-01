package company.ryzhkov

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import company.ryzhkov.components.Env.context

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import company.ryzhkov.controller._

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

import scala.concurrent.ExecutionContextExecutor

object Application extends App {
  implicit val system: ActorSystem                        = ActorSystem()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val routes = context.restController.route

  val bindingFuture = Http().bindAndHandle(context.restController.route, "localhost", 8080)

  println(s"Server started\nPress RETURN to stop...")

  StdIn.readLine()

  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
}
