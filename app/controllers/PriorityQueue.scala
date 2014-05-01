package controllers

import play.api.mvc._
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.json.JsResultException

object Root extends Controller {

  def index = Action { Ok(views.html.index("Moonfruit Buddy")) }

}