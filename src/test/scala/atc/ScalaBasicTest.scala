package atc
import verityco.util.Reporter
import akka.actor._
import akka.pattern._
import akka.testkit._
import akka.util.duration._
import akka.util.Timeout
import akka.dispatch.Await

class SBTestingActor extends Actor {

  override def receive = {
    case s: String =>
      Reporter.report.info("Received.")
      Reporter.report.info(s)
      Reporter.report.info("Done.")
    case _ => Reporter.report.info("Error!")
  }

}

class ScalaBasicTest extends TestActorDriver {
  implicit val timeout = Timeout(1 seconds)

  override def run() = {
    Reporter.report.info("Beginning.")
    implicit val as = ActorSystem("basic-factor-testing")

    val actor = TestActorRef[SBTestingActor]
    actor.tell("hello world 1")
    Reporter.report.info("Just finished telling, 1.")

    actor ! "hello world 2"
    Reporter.report.info("Just finished telling, 2.")

    as.shutdown() // Prevent leak of threads / memory
    Reporter.report.info("Done with test.")
  }
}