package models

import play.api.db._
import play.api.Play.current
import play.api.libs.json._

import anorm._
import anorm.SqlParser._

// type Answers = Map[String, (Double, Double)]

// User represents a user
// which persists as a SQL table (see conf/evolutions.default/1.sql)
case class User(
		id:         Int,
		name:       String,
		department: String,
		team:       String,
//		answers:    Answers
		)

object Users {

  // (de-)serialize to/from Json
  implicit object UserFormat extends Format[User] {
    def reads(json: JsValue) = JsSuccess(User(
      (json \ "id")         as[Int],
      (json \ "name")       as[String],
      (json \ "department") as[String],
      (json \ "team")       as[String],
    ))

    def writes(user: User) = JsObject(Seq(
      "id"         -> JsNumber(user.id),
      "name"       -> JsString(user.data),
      "department" -> JsString(user.data),
      "team"       -> JsString(user.data),
    ))
  }

  /**
   * Parse a User from a SQL ResultSet
   */
  val parseRS = {
    str("users.id") ~
    str("users.data") ~
    get[Long]("users.priority") map {
      case id ~ data ~ priority => Item(id, data, priority)
    }
  }

  /**
   * Get all items. For debugging.
   */
  def getAll: List[Item] = {
    DB.withConnection {
      implicit connection =>
        SQL("select * from users").as(Item.parseRS *)
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
        SQL("select * from users where id = {id}").on('id -> id).as(Item.parseRS.singleOpt)
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
        	select * from users
        	order by priority desc, inserted asc
        	limit 1
          """
        ).as(Item.parseRS.singleOpt)
        // now delete the row
        row match {
          case Some(i: Item) => {
            SQL("delete from users where id = {id}").on('id -> i.id).executeUpdate()
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
          insert into users
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

