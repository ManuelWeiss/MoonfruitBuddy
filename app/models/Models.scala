package models

import play.api.db._
import play.api.Play.current
import play.api.libs.json._

import anorm._
import anorm.SqlParser._

object PersistentCounter {
  def get: Long = {
    DB.withConnection {
      implicit connection =>
        SQL("select currval('counter')").as(scalar[Long].single)
          // on Postgres, only the following works correctly
          // (but unfortunately, this doesn't work on the default H2 DB):
//        SQL("select last_value from counter").as(scalar[Long].single)
    }
  }
  def increment: Long = {
    DB.withConnection {
      implicit connection =>
        SQL("select nextval('counter')").as(scalar[Long].single)
    }
  }
}

// Item represents an entry in the priority queue
// which persists as a SQL table (see conf/evolutions.default/1.sql)
case class Item(id: String, data: String, priority: Long)

object Item {

  // (de-)serialize to/from Json
  implicit object ItemFormat extends Format[Item] {
    def reads(json: JsValue) = JsSuccess(Item(
      (json \ "id").as[String],
      (json \ "data").as[String],
      (json \ "priority").as[Long]
    ))

    def writes(item: Item) = JsObject(Seq(
      "id"       -> JsString(item.id),
      "data"     -> JsString(item.data),
      "priority" -> JsNumber(item.priority)
    ))
  }

  /**
   * Parse an Item from a SQL ResultSet
   */
  val parseRS = {
    str("items.id") ~
    str("items.data") ~
    get[Long]("items.priority") map {
      case id ~ data ~ priority => Item(id, data, priority)
    }
  }

  /**
   * Get all items. For debugging.
   */
  def getAll: List[Item] = {
    DB.withConnection {
      implicit connection =>
        SQL("select * from items").as(Item.parseRS *)
    }
  }

  /**
   * Retrieve an Item by id.
   *
   * @param id The id of an Item.
   */
  def findById(id: String): Option[Item] = {
    DB.withConnection {
      implicit connection =>
        SQL("select * from items where id = {id}").on('id -> id).as(Item.parseRS.singleOpt)
    }
  }

  /**
   * Retrieve and delete Item with highest priority.
   */
  def pop: Option[Item] = {
    DB.withTransaction {
      implicit connection =>
        val row = SQL(
          """
        	select * from items
        	order by priority desc, inserted asc
        	limit 1
          """
        ).as(Item.parseRS.singleOpt)
        // now delete the row
        row match {
          case Some(i: Item) => {
            SQL("delete from items where id = {id}").on('id -> i.id).executeUpdate()
            row
          }
          case None => None
        }
    }
  }

  /**
   * Insert an item.
   *
   * @param item The Item object.
   */
  def insert(item: Item) = {
    DB.withConnection {
      implicit connection =>
        SQL(
          """
          insert into items
          (id, data, priority)
          values ( {id}, {data}, {priority} )
          """
        ).on(
          'id -> item.id,
          'data -> item.data,
          'priority -> item.priority
        ).executeUpdate()
    }
  }

}

