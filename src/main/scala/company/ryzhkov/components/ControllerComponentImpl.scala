package company.ryzhkov.components
import company.ryzhkov.controller.RestController

trait ControllerComponentImpl extends ControllerComponent {
  override val restController: RestController = new RestController
}
