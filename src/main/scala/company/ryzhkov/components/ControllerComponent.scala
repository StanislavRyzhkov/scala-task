package company.ryzhkov.components

import company.ryzhkov.controller.RestController

trait ControllerComponent extends ConfigurationComponent {
  val restController: RestController
}
