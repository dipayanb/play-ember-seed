package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

class DummyController extends Controller {
  def dummy() = Action {
    val json = Json.parse(
      """
        |{
        |   "name" : "Play Ember Seed",
        |   "author" : "Dipayan Bhowmick",
        |   "email" : "dipayan.bhowmick@gmail.com"
        |}
      """.stripMargin)
    Ok(json)
  }
}