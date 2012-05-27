package atc;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class TestActorDriverImpl implements TestActorDriver {
  String helloWorld = "Hello World";
  String helloWorld2 = "Hello world";
  Integer a = new Integer(34);

  @Override
  public void run() {
    ActorSystem system = ActorSystem.create("MySystem");
    ActorRef myActor = system.actorOf(new Props(TestActor.class), "myActor");
    Map<String, String> map = new HashMap<String, String>();
    map.put("hi", "world");
    myActor.tell(helloWorld + helloWorld2 + a);
    system.shutdown();
    system.awaitTermination();
  }

  public static void main(String[] args) {
    TestActorDriverImpl tadi = new TestActorDriverImpl();
    tadi.run();
  }

}
