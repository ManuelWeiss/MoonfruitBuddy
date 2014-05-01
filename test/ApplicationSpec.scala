package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json._

class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on inexistent path" in {
      running(FakeApplication()) {
        route(FakeRequest(GET, "/inexistent_path")) must beNone
      }
    }

    "say Domo arigato" in {
      running(FakeApplication()) {
        val response = route(FakeRequest(GET, controllers.routes.Root.index().url)).get

        status(response) must beEqualTo(OK)
        contentType(response) must beSome.which(_ == "text/plain")
        contentAsString(response) must be_==("Domo arigato, Mr Roboto.")
      }
    }

    "implement a counter" in {
      running(FakeApplication()) {
        var response = route(FakeRequest(GET, controllers.routes.Counter.get().url)).get

        status(response) must beEqualTo(OK)
        contentType(response) must beSome.which(_ == "text/plain")
        contentAsString(response).toInt mustEqual 0
        header("X-HELLO-MOBIFY-ROBOT", response) must beSome.which(_ == "hi")

        // now increment via POST
        response = route(FakeRequest(POST, controllers.routes.Counter.increment().url)).get

        status(response) must beEqualTo(OK)
        contentType(response) must beSome.which(_ == "text/plain")
        contentAsString(response) mustEqual ""

        // check new value
        response = route(FakeRequest(GET, controllers.routes.Counter.get().url)).get

        status(response) must beEqualTo(OK)
        contentType(response) must beSome.which(_ == "text/plain")
        contentAsString(response).toInt mustEqual 1
        header("X-HELLO-MOBIFY-ROBOT", response) must beSome.which(_ == "hi")
      }
    }

    "implement a cached-timestamp" in {
      running(FakeApplication()) {
        var response = route(FakeRequest(GET, controllers.routes.CachedTimestamp.get().url)).get

        status(response) must beEqualTo(OK)
        contentType(response) must beSome.which(_ == "text/plain")
        val value = contentAsString(response).toInt
        value must beGreaterThan(1362916989)  // 2013-03-10

        // get another one
        response = route(FakeRequest(GET, controllers.routes.CachedTimestamp.get().url)).get

        var value2 = contentAsString(response).toInt
        value2 mustEqual value

        // now wait for a while to get a new one
        Thread.sleep(16 * 1000)
        response = route(FakeRequest(GET, controllers.routes.CachedTimestamp.get().url)).get

        value2 = contentAsString(response).toInt
        value2 must beGreaterThan(value)
      }
    }

    "echo input" in {
      running(FakeApplication()) {
        val response = route(FakeRequest(POST, controllers.routes.Echo.call().url).withFormUrlEncodedBody("foo" -> "bar")).get
        status(response) must beEqualTo(OK)
        contentType(response) must beSome.which(_ == "application/json")
        contentAsString(response) mustEqual "{\"foo\":[\"bar\"]}"
      }
    }

    "implement kvstore as a priority queue" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        // try to get not existing id
        var response = route(FakeRequest(GET, controllers.routes.PriorityQueue.get("abc").url)).get

        status(response) must beEqualTo(BAD_REQUEST)
        contentType(response) must beSome.which(_ == "text/plain")
        contentAsString(response) mustEqual "identifier does not exist"

        // now put some items into queue
        response = route(
                    FakeRequest(PUT, controllers.routes.PriorityQueue.insert().url)
                    .withJsonBody(JsObject(Seq(
                      "id"       -> JsString("id01"),
                      "data"     -> JsString("data01"),
                      "priority" -> JsNumber(5)
                    )))
                  ).get
        status(response) must beEqualTo(OK)
        contentType(response) must beSome.which(_ == "text/plain")
        response = route(
          FakeRequest(PUT, controllers.routes.PriorityQueue.insert().url)
            .withJsonBody(JsObject(Seq(
            "id"       -> JsString("id02"),
            "data"     -> JsString("data02"),
            "priority" -> JsNumber(1)
          )))
        ).get
        status(response) must beEqualTo(OK)
        contentType(response) must beSome.which(_ == "text/plain")
        response = route(
          FakeRequest(PUT, controllers.routes.PriorityQueue.insert().url)
            .withJsonBody(JsObject(Seq(
            "id"       -> JsString("id03"),
            "data"     -> JsString("data03"),
            "priority" -> JsNumber(5)
          )))
        ).get
        status(response) must beEqualTo(OK)
        contentType(response) must beSome.which(_ == "text/plain")

        // try to get one via id
        response = route(FakeRequest(GET, controllers.routes.PriorityQueue.get("id01").url)).get

        status(response) must beEqualTo(OK)
        contentType(response) must beSome.which(_ == "application/json")
        contentAsString(response) mustEqual "{\"data\":\"data01\"}"

        // now pop them - we expect them in this order (by priority, FIFO):
        // id01, id03, id02
        response = route(FakeRequest(POST, controllers.routes.PriorityQueue.pop().url)).get

        status(response) must beEqualTo(OK)
        contentType(response) must beSome.which(_ == "application/json")
        contentAsString(response) mustEqual "{\"data\":\"data01\"}"

        response = route(FakeRequest(POST, controllers.routes.PriorityQueue.pop().url)).get

        status(response) must beEqualTo(OK)
        contentType(response) must beSome.which(_ == "application/json")
        contentAsString(response) mustEqual "{\"data\":\"data03\"}"

        response = route(FakeRequest(POST, controllers.routes.PriorityQueue.pop().url)).get

        status(response) must beEqualTo(OK)
        contentType(response) must beSome.which(_ == "application/json")
        contentAsString(response) mustEqual "{\"data\":\"data02\"}"

      }
    }
  }
}