package atc
import verityco.util.Reporter
import akka.actor._
import akka.pattern._
import akka.testkit._
import akka.util.duration._
import akka.util.Timeout
import akka.dispatch.Await

case object RESPONSE

// Informs the system about the messages it receives.
class FullActorSysTestActor0 extends UntypedActor {
  override def onReceive(o: Any) = o match {
    case s: String =>
      println("Got a message! : " + s)
      Reporter.report.info(s)
    case _ => Reporter.report.info("Error!")
  }
}
// Passes along messages to the friend
class FullActorSysTestActor1 extends UntypedActor {

  override def onReceive(o: Any) = o match {
    case s: String =>
      println("Trying to send towards actor: " + context.actorFor("../a0"))
      context.actorFor("../a0") ! s
    case m => Reporter.report.info("Error!")
  }
}

// This should not have any threading errors.
class FullActorSystemTest extends TestActorDriverExtended {
  implicit val timeout = Timeout(1 seconds)

  override def run() = {
    implicit val as = ActorSystem("basic-factor-testing")

    val a0 = as.actorOf(Props[FullActorSysTestActor0], name = "a0")
    val a1 = as.actorOf(Props[FullActorSysTestActor1], name = "a1")

    println("a0 is: " + a0)

    a0 ! "hw1"
    a1 ! "hw2"

    Await.result(gracefulStop(a0, 5 seconds), 6 seconds)

    as.shutdown() // Prevent leak of threads / memory
    as.awaitTermination(5 seconds) // Make sure everything is now happy.
  }
}
