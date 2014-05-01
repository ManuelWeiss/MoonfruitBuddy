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


	
	lazy val q = for (i <- 1 to 5) yield Question(
						s"test-q$i",
						s"Silly question No. $i",
						"not at all - totally")
	
	def getAll = q
}

