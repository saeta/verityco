package verityco.logic;

import verityco.util.Reporter;

public class ObjectState {
  // Note: a weak reference to the pointed-to object is not maintained for space
  // efficiency reasons.
  ObjectStateEnum state = ObjectStateEnum.PRISTINE;
  Object owningActor = null;

  /**
   * Check the state for a write in this object.
   * 
   * @param writingActor
   *          Actor doing write.
   * @param obj
   *          Passed in for error reporting
   */
  public void write(Object writingActor, Object obj) {
    if (state == ObjectStateEnum.PRISTINE || state == ObjectStateEnum.READ
        || state == ObjectStateEnum.WRITE) {
      if (owningActor == null || owningActor == writingActor) {
        state = ObjectStateEnum.WRITE;
        owningActor = writingActor;
      } else {
        if (state == ObjectStateEnum.WRITE) {
          Reporter.report.reportWriteWriteConflict(writingActor, owningActor,
              obj);
        } else {
          Reporter.report.reportReadWriteConflict(writingActor, owningActor,
              obj);
        }
      }
    } else if (state == ObjectStateEnum.MULTIREAD) {
      Reporter.report.reportMultiReadWriteConflict(writingActor, obj);
    } else if (state == ObjectStateEnum.ERROR) {
      // Do nothing; error suppression
    }
  }

  public void read(Object readingActor, Object obj) {
    if (state == ObjectStateEnum.PRISTINE || state == ObjectStateEnum.READ) {
      if (owningActor == null || owningActor == readingActor) {
        state = ObjectStateEnum.READ;
        owningActor = readingActor;
      } else {
        state = ObjectStateEnum.MULTIREAD;
      }
    } else if (state == ObjectStateEnum.WRITE) {
      if (owningActor == readingActor) {
        // Do nothing
      } else {
        Reporter.report.reportWriteReadConflict(readingActor, owningActor, obj);
      }
    } else if (state == ObjectStateEnum.MULTIREAD) {
      // all good
    } else if (state == ObjectStateEnum.ERROR) {
      // Do nothing; error suppression
    }
  }

  public void setOwning(Object newOwningActor) {
    if (state == ObjectStateEnum.READ || state == ObjectStateEnum.WRITE
        || state == ObjectStateEnum.PRISTINE) {
      state = ObjectStateEnum.PRISTINE;
      owningActor = newOwningActor;
    } else if (state == ObjectStateEnum.MULTIREAD) {
      // Do nothing, stay in multi-read.
    } else if (state == ObjectStateEnum.ERROR) {
      // Do nothing, we're already in the error state.
    }
  }
}
