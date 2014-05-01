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
		scaleTo:      Double = 10.0)

object Questions {

	val q = for (i <- 1 to 5) yield Question(
						s"test-q$i",
						s"Silly question No. $i",
						"not at all - totally")
	
}

