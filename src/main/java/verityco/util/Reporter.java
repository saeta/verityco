package verityco.util;

public class Reporter {

  public static Reporter report = new Reporter();

  public void report(String error) {
    System.err.println(error);
  }

  /**
   * When in state write, and actor readingActor reads.
   * 
   * @param readingActor
   * @param writingActor
   * @param raced
   */
  public void reportWriteReadConflict(Object readingActor, Object writingActor,
      Object raced) {
    System.err.println("There is a write-read conflict on object: " + raced
        + ". Actor " + readingActor
        + " attempted to read the object after actor " + writingActor
        + " already wrote to it.");
  }

  /**
   * When in state read, and actor writingActor writes (does not have chown).
   * 
   * @param writingActor
   * @param readingActor
   * @param raced
   */
  public void reportReadWriteConflict(Object writingActor, Object readingActor,
      Object raced) {
    System.err.println("There is a read-write conflict on object:" + raced
        + ". Actor " + writingActor
        + " attempted to write to the object after actor " + readingActor
        + " already read from it.");
  }

  /**
   * When in write state, non-owning actor errorActor writes to raced owned by
   * owningActor
   * 
   * @param errorActor
   * @param owningActor
   * @param raced
   */
  public void reportWriteWriteConflict(Object errorActor, Object owningActor,
      Object raced) {
    System.err.println("There is a write-write conflict on object: " + raced
        + ". Actor " + errorActor
        + " attempted to write to the object while actor " + owningActor
        + " already wrote to it (and has chown).");
  }

  /**
   * When an actor writes to the raced object, which is in multi-read state.
   * 
   * @param errorActor
   * @param raced
   */
  public void reportMultiReadWriteConflict(Object errorActor, Object raced) {
    System.err.println("There is a multi-read/write conflict on object: "
        + raced + ". Actor " + errorActor
        + "attempted to write to the object, but that object is already"
        + " in the multi-read state.");
  }

  // TODO: report threading errors if any non-actor thread touches an object (?)

  public void info(String informational) {
    System.out.println(informational);
  }

}
