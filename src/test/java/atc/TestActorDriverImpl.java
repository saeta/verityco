package atc;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class TestActorDriverImpl implements TestActorDriver {
	public void run() {
		ActorSystem system = ActorSystem.create("MySystem");
		ActorRef myActor = system.actorOf(new Props(TestActor.class), "myActor");
		myActor.tell("Hello World!");
		system.shutdown();
		system.awaitTermination();
	}
}
