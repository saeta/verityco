package atc
import verityco.util.Reporter
import akka.actor._
import akka.pattern._
import akka.testkit._
import akka.util.duration._
import akka.util.Timeout
import akka.dispatch.Await

class TestingActor extends UntypedActor {
  override def onReceive(o: Any): Unit = o match {
    case s: String => // Do nothing
    case m: java.util.Map[String, String] =>
      Reporter.report.info("Received.")
      m.put("Hello", "world")
      sender ! "Hi"
  }
}

class BasicActorThreading extends TestActorDriver {
  implicit val timeout = Timeout(1 seconds)

  override def run() = {
    Reporter.report.info("Beginning.")
    implicit val as = ActorSystem("basic-factor-testing")
    Reporter.report.info("Created actor system.")

    val actor = TestActorRef[TestingActor]
    actor.tell("hello world")
    Reporter.report.info("Just finished telling.")

    val myMap = new java.util.HashMap[String, String]()
    val res = Await.result(actor ? myMap, 1 seconds).asInstanceOf[String]
    Reporter.report.info("Got back: " + res)
    as.shutdown() // Prevent leak of threads / memory
    Reporter.report.info("Done.")
  }

  def run1() = {
    Reporter.report.info("Beginning.")
    val as = ActorSystem("basic-factor-testing")
    Reporter.report.info("Created actor system.")

    val actor = as.actorOf(Props(new UntypedActor {
      override def onReceive(o: Any): Unit = o match {
        case m: java.util.Map[String, String] =>
          Reporter.report.info("Received.")
          m.put("Hello", "world")
          sender ! "Hi"
      }
    }))
    val future = actor ? new java.util.HashMap[String, String]()
    future.map {
      case None => "foo"
    }

  }

}