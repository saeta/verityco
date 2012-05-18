package atc;

import akka.actor.UntypedActor;

public class TestActor extends UntypedActor {

  public void onReceive(Object message) {
    if (message instanceof String)
      System.out.println("Received string message.");
    else
      unhandled(message);
  }
}
