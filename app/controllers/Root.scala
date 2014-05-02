package controllers

import play.api.mvc._
import play.api.Play.current

object Root extends Controller {

  def index = Action { Ok(views.html.index("Moonfruit Buddy")) }

}