package company.ryzhkov.components
import company.ryzhkov.controller.RestController

trait ControllerComponentImpl extends ControllerComponent {
  this: ConfigurationComponent =>

  override lazy val restController: RestController = new RestController(system)(executionContext)
}
