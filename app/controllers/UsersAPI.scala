package controllers

import play.api.mvc._
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.json.JsResultException
import models.User
import models.Answer

// controller answers to GET, PUT and POST requests
// - GET: returns the item for the given id
// - PUT: insert new item into queue, expects Json-formatted body like this:
//         {"id": "abcd12345", "data": "sample-value", "priority": 0}
// - POST: returns the item with the highest priority (FIFO) and removes it from the queue
// (see Models.scala for Item class)
object UsersAPI extends Controller {

  def get(id: String) = Action {
    User.findById(id) match {
      case Some(user: User) => Ok(Json.toJson(user))
      case None => BadRequest("user does not exist")
    }
  }

  def getAnswers(id: String) = Action {
      Ok(Json.toJson(Answer.allForUser(id)))
  }

  def update = Action(parse.json) {
    request => {
      val user: User = request.body.as[User]  // see class User for conversion of Json
      try {
        User.update(user) match {
        	case Some(u: User) => Ok(Json.toJson(u))
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
        User.insert(json.as[User])  // see class User for conversion of Json
        Ok("")
      } catch {
        case e: JsResultException => BadRequest(s"invalid JSON: $e")
      }
    }
  }

  // for debugging - returns all users
  def getAll = Action {
    Ok(Json.toJson(User.getAll))
  }

}