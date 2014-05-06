package controllers

import play.api.mvc._
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.json.JsResultException
import play.api.libs.json._
import models.User
import models.Buddy

object BuddiesAPI extends Controller {

  def findBuddy(id: String) = Action {
  	val res = for ((u, d) <- Buddy.findBuddy(id)) yield JsObject(Seq(
  			"distance" -> JsNumber(d),
  			"user"     -> JsString(u)
  			))
    Ok(Json.toJson(res))
  }

}