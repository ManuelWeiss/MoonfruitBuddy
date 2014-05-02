package controllers

import play.api.mvc._
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.json.JsResultException
import models.User

object BuddiesAPI extends Controller {

  def findBuddy(id: String) = Action {
    Ok(Json.toJson(User.findBuddy(id)))
  }

}