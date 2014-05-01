package models

import play.api.db._
import play.api.Play.current
import play.api.libs.json._

import anorm._
import anorm.SqlParser._

// Question represents a single question
case class Question(
		id:           String,
		text:         String,
		scaleExplain: String,
		scaleFrom:    Double = 0.0,
		scaleTo:      Double = 10.0
		)

object Question {

	  // (de-)serialize to/from Json
  implicit object QuestionFormat extends Format[Question] {
    def reads(json: JsValue) = JsSuccess(Question(
      (json \ "id").as[String],
      (json \ "text").as[String],
      (json \ "scaleExplain").as[String],
      (json \ "scaleFrom").as[Double],
      (json \ "scaleTo").as[Double]
    ))

    def writes(q: Question) = JsObject(Seq(
      "id"           -> JsString(q.id),
      "text"         -> JsString(q.text),
      "scaleExplain" -> JsString(q.scaleExplain),
      "scaleFrom"    -> JsNumber(q.scaleFrom),
      "scaleTo"      -> JsNumber(q.scaleTo)
    ))
  }

  /**
   * Parse a Question from a SQL ResultSet
   */
  val parseRS = {
    str("questions.id") ~
    str("questions.text") ~
    str("questions.scale_explain") ~
    get[Double]("questions.scale_from") ~
    get[Double]("questions.scale_to") map {
      case id ~ text ~ scale_explain ~ scale_from ~ scale_to =>
      		Question(id, text, scale_explain, scale_from, scale_to)
    }
  }

  /**
   * Get all questions.
   */
  def getAll: List[Question] = {
    DB.withConnection {
      implicit connection =>
        SQL("select * from questions").as(Question.parseRS *)
    }
  }
}

