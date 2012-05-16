package verityco;

import akka.actor.ActorRef;

public class ThreadState {

  public static final ThreadLocal<ActorRef> threadState = new ThreadLocal<ActorRef>() {

    @Override
    protected ActorRef initialValue() {
      return null;
    }
  };

}
