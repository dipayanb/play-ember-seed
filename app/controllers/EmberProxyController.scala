package controllers

import javax.inject.Inject

import play.api._
import play.api.mvc._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class EmberProxyController @Inject() (configuration: Configuration, env: Environment) extends Controller {

  def index = Action.async { request =>
    if( env.mode == Mode.Dev) {
      val emberPort = configuration.getInt("ember.application.port").getOrElse(4200)
      Logger.info(s"Redirecting to the UI application with port $emberPort")
      Future { Redirect(s"http://localhost:$emberPort", TEMPORARY_REDIRECT) }
    } else {
      Assets.at("/public", "index.html").apply(request)
    }

  }
}
