package models

import play.api.db._
import play.api.Play.current
import play.api.libs.json._

import anorm._
import anorm.SqlParser._

import scala.collection.mutable.HashMap

// Answer represents a single answer
case class Answer(
	user_id: String,
	question_id: String,
	answer: Double = 5.0)

object Answer {

	// (de-)serialize to/from Json
	implicit object AnswerFormat extends Format[Answer] {
		def reads(json: JsValue) = JsSuccess(Answer(
			(json \ "user_id").as[String],
			(json \ "question_id").as[String],
			(json \ "answer").as[Double]))

		def writes(q: Answer) = JsObject(Seq(
			"user_id" -> JsString(q.user_id),
			"question_id" -> JsString(q.question_id),
			"answer" -> JsNumber(q.answer)))
	}

	/**
	 * Parse an Answer from a SQL ResultSet
	 */
	val parseAnswerRS = {
		str("answers.user_id") ~
			str("answers.question_id") ~
			get[Double]("answers.answer") map {
				case user_id ~ question_id ~ answer =>
					Answer(user_id, question_id, answer)
			}
	}

	/**
	 * Get all Answers as a List.
	 */
	def getAll: List[Answer] = {
		DB.withConnection {
			implicit connection =>
				SQL("select * from answers").as(parseAnswerRS *)
		}
	}

	/**
	 * Get all Answers as a Map keyed by user_id.
	 */
	def getMapByUser = {
		val answersByUser = HashMap[String, Map[String, Double]]()
		getAll.foreach { case Answer(u, q, a) =>
			val answers = answersByUser.getOrElse(u, Map[String, Double]())
			answersByUser(u) = answers + (q -> a)
		}
		answersByUser
	}

	/**
	 * Get all answers for a user.
	 */
	def allForUser(user_id: String): List[Answer] = {
		DB.withConnection {
			implicit connection =>
				SQL("select * from answers where user_id = {user_id}").on(
					'user_id -> user_id).as(parseAnswerRS *)
		}
	}

	/**
	 * Retrieve an answer by user_id & question_id.
	 *
	 * @param user_id The id of the user.
	 * @param question_id The id of the question.
	 */
	def findById(user_id: String, question_id: String): Option[Answer] = {
		DB.withConnection {
			implicit connection =>
				SQL("""
        	select * from answers
        		where user_id = {user_id}
        		and question_id = {question_id}
        	""").on(
					'user_id -> user_id,
					'question_id -> question_id).as(parseAnswerRS.singleOpt)
		}
	}

	/**
	 * Update or insert an existing answer.
	 *
	 * @param answer The answer object.
	 */
	def updateOrInsert(answer: Answer): Option[Answer] = {
		DB.withConnection {
			implicit connection =>
				SQL("""
	          merge into answers
	          (user_id, question_id, answer)
	          values ( {user_id}, {question_id}, {answer} )
        	""").on(
					'user_id -> answer.user_id,
					'question_id -> answer.question_id,
					'answer -> answer.answer).executeUpdate()
		}
		Some(answer)
	}

	/**
	 * Update an existing answer.
	 *
	 * @param answer The answer object.
	 */
	def update(answer: Answer): Option[Answer] = {
		DB.withConnection {
			implicit connection =>
				SQL("""
        	update answers set answer = {answer}
        		where user_id = {user_id}
        		and question_id = {question_id}
        	""").on(
					'user_id -> answer.user_id,
					'question_id -> answer.question_id,
					'answer -> answer.answer).executeUpdate()
		}
		Some(answer)
	}

	/**
	 * Insert a answer.
	 *
	 * @param answer The Answer object.
	 */
	def insert(answer: Answer) = {
		DB.withConnection {
			implicit connection =>
				SQL(
					"""
          insert into answers
          (user_id, question_id, answer)
          values ( {user_id}, {question_id}, {answer} )
          """).on(
						'user_id -> answer.user_id,
						'question_id -> answer.question_id,
						'answer -> answer.answer).executeUpdate()
		}
	}

}

