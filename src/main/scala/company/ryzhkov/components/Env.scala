package company.ryzhkov.components

import company.ryzhkov.components.configuration.ConfigurationComponentImpl
import company.ryzhkov.components.controller.ControllerComponentImpl

object Env {
  type Context = ControllerComponentImpl with ConfigurationComponentImpl
  val context: Context = new ControllerComponentImpl with ConfigurationComponentImpl {}
}
