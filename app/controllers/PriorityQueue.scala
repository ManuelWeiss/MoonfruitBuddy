package controllers

import play.api.mvc._
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.json.JsResultException
import models._

// controller answers to GET, PUT and POST requests
// - GET: returns the item for the given id
// - PUT: insert new item into queue, expects Json-formatted body like this:
//         {"id": "abcd12345", "data": "sample-value", "priority": 0}
// - POST: returns the item with the highest priority (FIFO) and removes it from the queue
// (see Models.scala for Item class)
object PriorityQueue extends Controller {

  def insert = Action(parse.json) {
    request => {
      val json = request.body
      try {
        // insert into db
        Item.insert(json.as[Item])  // see class Item for conversion of Json
        Ok("")
      } catch {
        case e: JsResultException => BadRequest("invalid JSON")
      }
    }
  }

  def pop = Action {
    Item.pop match {
      case Some(item: Item) => Ok(Json.obj("data" -> item.data))
      case None => BadRequest("queue is empty")
    }
  }

  def get(id: String) = Action {
    Item.findById(id) match {
      case Some(item: Item) => Ok(Json.obj("data" -> item.data))
      case None => BadRequest("identifier does not exist")
    }
  }

  // for debugging - returns all items in the queue
  def list = Action {
    Ok(Json.toJson(Item.getAll))
  }

}