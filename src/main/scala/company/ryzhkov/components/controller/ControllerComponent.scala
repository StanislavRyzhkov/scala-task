package company.ryzhkov.components.controller

import company.ryzhkov.components.configuration.ConfigurationComponent
import company.ryzhkov.controller.RestController

trait ControllerComponent extends ConfigurationComponent {
  def restController: RestController
}
