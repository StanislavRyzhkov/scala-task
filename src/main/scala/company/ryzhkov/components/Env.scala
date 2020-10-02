package company.ryzhkov.components

object Env {
  type Context = ControllerComponentImpl with ConfigurationComponentImpl
  val context: Context = new ControllerComponentImpl with ConfigurationComponentImpl {}
}
