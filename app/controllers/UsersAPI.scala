package controllers

import play.api.mvc._
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.json.JsResultException
import models.Users

// controller answers to GET, PUT and POST requests
// - GET: returns the item for the given id
// - PUT: insert new item into queue, expects Json-formatted body like this:
//         {"id": "abcd12345", "data": "sample-value", "priority": 0}
// - POST: returns the item with the highest priority (FIFO) and removes it from the queue
// (see Models.scala for Item class)
object UsersAPI extends Controller {

  def get(id: Int) = Action {
    Users.findById(id) match {
      case Some(user: User) => Ok(Json.obj("data" -> user.data))
      case None => BadRequest("user does not exist")
    }
  }

  def update = TODO

  def create = Action(parse.json) {
    request => {
      val json = request.body
      try {
        // insert into db
        Users.insert(json.as[User])  // see class User for conversion of Json
        Ok("")
      } catch {
        case e: JsResultException => BadRequest(s"invalid JSON: $e")
      }
    }
  }

  // for debugging - returns all users
  def list = Action {
    Ok(Json.toJson(Users.getAll))
  }

}