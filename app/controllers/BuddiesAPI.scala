package controllers

import play.api.mvc._
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.json.JsResultException
import play.api.libs.json._
import models.User

object BuddiesAPI extends Controller {

  def findBuddy(id: String) = Action {
    Ok(User.findBuddy(id))
  }

  def findBuddyDummy(id: String) = Action {
	val rnd = scala.util.Random
  	val usersWithDist = User.getAll.map(u => Seq(
  			"distance" -> JsNumber(10 * rnd.nextDouble),
  			"user"     -> Json.toJson(u)
  			)
  			).map(JsObject(_))
    Ok(Json.toJson(usersWithDist))
  }

}