package atc;

import java.util.HashMap;
import java.util.Map;

import akka.actor.Actor;

public abstract class TestActorDriverExtended implements TestActorDriver {

  Map<String, Actor> actorMap = new HashMap<String, Actor>();
  Map<String, Object> messageMap = new HashMap<String, Object>();

  public void setActorPointer(String name, Actor actor) {
    actorMap.put(name, actor);
  }

  public Actor getActorPointer(String actorName) {
    return actorMap.get(actorName);
  }

  public void setMessage(String name, Object message) {
    messageMap.put(name, message);
  }

  public Object getMessage(String messageName) {
    return messageMap.get(messageName);
  }
}
