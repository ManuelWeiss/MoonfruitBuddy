package models

import play.api.db._
import play.api.Play.current
import play.api.libs.json._

import anorm._
import anorm.SqlParser._

import models.Answer

// User represents a user
// which persists as a SQL table (see conf/evolutions.default/1.sql)
case class User(
		id:         String,
		name:       String,
		department: String,
		team:       String //,
//		answers:    Answers
		)

object User {

  // (de-)serialize to/from Json
  implicit object UserFormat extends Format[User] {
    def reads(json: JsValue) = JsSuccess(User(
      (json \ "id").as[String],
      (json \ "name").as[String],
      (json \ "department").as[String],
      (json \ "team").as[String]
    ))

    def writes(user: User) = JsObject(Seq(
      "id"         -> JsString(user.id),
      "name"       -> JsString(user.name),
      "department" -> JsString(user.department),
      "team"       -> JsString(user.team)
    ))
  }

  /**
   * Parse a User from a SQL ResultSet
   */
  val parseRS = {
    str("users.id") ~
    str("users.name") ~
    str("users.department") ~
    str("users.team") map {
      case id ~ name ~ department ~ team => User(id, name, department, team)
    }
  }

  /**
   * Get all items. For debugging.
   */
  def getAll: List[User] = {
    DB.withConnection {
      implicit connection =>
        SQL("select * from users").as(User.parseRS *)
    }
  }

  /**
   * Retrieve a user by id.
   *
   * @param id The id of the user.
   */
  def findById(id: String): Option[User] = {
    DB.withConnection {
      implicit connection =>
        SQL("select * from users where id = {id}").on('id -> id).as(User.parseRS.singleOpt)
    }
  }

  /**
   * Find buddies for a given user
   *
   * @param id The id of the user.
   */
  def findBuddy(id: String) = {
	  val answers = Answer.getMapByUser
	  answers.mkString("\n")
  }

  /**
   * Update an existing user.
   *
   * @param id The id of the user.
   */
  def update(user: User): Option[User] = {
    DB.withConnection {
      implicit connection =>
        SQL("""
        	update users set (name, department, team) = 
        		( {name}, {department}, {team} ) where id = {id}
        	"""
        	).on(
	          'id         -> user.id,
	          'name       -> user.name,
	          'department -> user.department,
	          'team       -> user.team
        		).executeUpdate()
    }
    Some(user)
  }

  /**
   * Insert a user.
   *
   * @param user The User object.
   */
  def insert(user: User) = {
    DB.withConnection {
      implicit connection =>
        SQL(
          """
          insert into users
          (id, name, department, team)
          values ( {id}, {name}, {department}, {team} )
          """
        ).on(
          'id         -> user.id,
          'name       -> user.name,
          'department -> user.department,
          'team       -> user.team
        ).executeUpdate()
    }
  }

}

