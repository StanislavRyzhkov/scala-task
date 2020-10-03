package company.ryzhkov.components.controller

import company.ryzhkov.components.configuration.ConfigurationComponent
import company.ryzhkov.controller.RestController

trait ControllerComponentImpl extends ControllerComponent {
  this: ConfigurationComponent =>

  override lazy val restController: RestController = new RestController(system)(executionContext)
}
