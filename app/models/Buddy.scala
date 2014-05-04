package models

import play.api.db._
import play.api.Play.current
import play.api.libs.json._

import models.Answer

object Buddy {

	/**
	 * Find buddies for a given user
	 *
	 * @param id The id of the user.
	 */
	def findBuddy(id: String): String = {
	  def squared(x: Double) = x*x
	  val list = Answer.getBuddyAnswers(id).map{
	      case (uid, qid, a1, a2) => (uid, squared(a1 - a2))
	  	}
	  val buildMapOfSumsFunc = (x: Map[String, Double], y: (String, Double)) =>
	    			x + (y._1 -> (x.getOrElse(y._1, 0.0) + y._2))
	  val user_idsToDistances = list.foldLeft(Map[String, Double]())( buildMapOfSumsFunc ).mapValues(Math.sqrt)
	  user_idsToDistances.mkString("\n")
	}

}

