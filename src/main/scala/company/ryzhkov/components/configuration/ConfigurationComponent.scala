package company.ryzhkov.components.configuration

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContextExecutor

trait ConfigurationComponent {
  def system: ActorSystem
  def executionContext: ExecutionContextExecutor
}
