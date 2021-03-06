package company.ryzhkov.components.configuration

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContextExecutor

trait ConfigurationComponentImpl extends ConfigurationComponent {
  override lazy val system: ActorSystem                        = ActorSystem()
  override lazy val executionContext: ExecutionContextExecutor = system.dispatcher
}
