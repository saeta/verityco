package verityco.logic;

public class ObjectState {
  ObjectStateEnum state = ObjectStateEnum.PRISTINE;
  Object owningActor = null;

  public void write(Object writingActor) {
    if (state == ObjectStateEnum.PRISTINE || state == ObjectStateEnum.READ
        || state == ObjectStateEnum.WRITE) {
      if (owningActor == writingActor) {
        state = ObjectStateEnum.WRITE;
      } else {
        // Error!!!
      }
    } else if (state == ObjectStateEnum.MULTIREAD) {
      // Error
    } else if (state == ObjectStateEnum.ERROR) {
      // Do nothing; error suppression
    }
  }

  public void read(Object readingActor) {
    if (state == ObjectStateEnum.PRISTINE || state == ObjectStateEnum.READ) {
      if (owningActor == readingActor) {
        state = ObjectStateEnum.READ;
      } else {
        state = ObjectStateEnum.MULTIREAD;
      }
    } else if (state == ObjectStateEnum.WRITE) {
      if (owningActor == readingActor) {
        // Do nothing
      } else {
        // Error
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
