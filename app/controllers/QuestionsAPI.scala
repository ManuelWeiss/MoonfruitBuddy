package controllers

import play.api.mvc._
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.json.JsResultException
import models.Questions

// controller answers to GET requests
// - GET: returns the list of defined questions
object QuestionsAPI extends Controller {

  def getAll = Action {
    Ok(Json.toJson(Questions.q))
  }

}