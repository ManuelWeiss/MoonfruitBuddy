package controllers

import play.api.mvc._
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.json.JsResultException
import models.Answer

object AnswersAPI extends Controller {

  def get(user_id: String, question_id: String) = Action {
    Answer.findById(user_id, question_id) match {
      case Some(answer: Answer) => Ok(Json.toJson(answer))
      case None => BadRequest("answer does not exist")
    }
  }

  def update = Action(parse.json) {
    request => {
      val answer: Answer = request.body.as[Answer]  // see class Answer for conversion of Json
      try {
        Answer.update(answer) match {
        	case Some(u: Answer) => Ok(Json.toJson(u))
        	case None => BadRequest("something went wrong")
        }
      } catch {
        case e: JsResultException => BadRequest(s"invalid JSON: $e")
      }
    }
  }

  def create = Action(parse.json) {
    request => {
      val json = request.body
      try {
        // insert into db
        Answer.insert(json.as[Answer])  // see class Answer for conversion of Json
        Ok("")
      } catch {
        case e: JsResultException => BadRequest(s"invalid JSON: $e")
      }
    }
  }

  // for debugging - returns all answers
  def getAll = Action {
    Ok(Json.toJson(Answer.getAll))
  }

}