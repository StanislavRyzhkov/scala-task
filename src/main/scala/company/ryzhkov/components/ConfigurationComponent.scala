package company.ryzhkov.components

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContextExecutor

trait ConfigurationComponent {
  val system: ActorSystem
  val executionContext: ExecutionContextExecutor
}
