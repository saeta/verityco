package atc
import verityco.util.Reporter
import akka.actor._
import akka.pattern._
import akka.testkit._
import akka.util.duration._
import akka.util.Timeout
import akka.dispatch.Await

case class RWTestingResponse

class RWTestingActorRead extends UntypedActor {
  override def onReceive(o: Any): Unit = o match {
    case m: java.util.Map[String, String] =>
      m.get("Hello")
      sender ! RWTestingResponse
  }
  override def toString = "reader"
}

class RWTestingActorWrite extends UntypedActor {
  override def onReceive(o: Any): Unit = o match {
    case m: java.util.Map[String, String] =>
      m.put("Hello", "world")
      sender ! RWTestingResponse
  }

  override def toString = "writer"
}
class ReadWriteActorThreading extends TestActorDriverExtended {
  implicit val timeout = Timeout(1 seconds)
  implicit val as = ActorSystem("read-write-test")

  override def run() = {
    val reader = TestActorRef[RWTestingActorRead]
    val writer = TestActorRef[RWTestingActorWrite]
    setActorPointer("reader", reader.underlyingActor)
    setActorPointer("writer", writer.underlyingActor)

    val myMap = new java.util.HashMap[String, String]()
    Await.result(reader ? myMap, 1 seconds)
    Await.result(writer ? myMap, 1 seconds)

    setMessage("msg", myMap)

    as.shutdown() // Prevent leak of threads / memory
  }

}