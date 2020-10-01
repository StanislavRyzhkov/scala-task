package company.ryzhkov.components

object Env {
  type Context = ControllerComponentImpl

  val context: Context = new ControllerComponentImpl {}
}
