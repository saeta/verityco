package verityco.logic;

import verityco.inst.ThreadState;
import verityco.util.Reporter;

public class Panopticon {
  public static Panopticon panopticon = new Panopticon();

  public void load(Object o) {
    if (ThreadState.threadState.get() != null) {
      if (objects.containsKey(o)) {
        objects.get(o).read(ThreadState.threadState.get(), o);
      } else {
        ObjectState os = new ObjectState();
        os.read(ThreadState.threadState.get(), o);
        objects.put(o, os);
      }
    }
  }

  public void loadStatic() {
    Reporter.report.report("Static load of object ");
  }

  public void store(Object o) {
    if (ThreadState.threadState.get() != null) {
      if (objects.containsKey(o)) {
        objects.get(o).write(ThreadState.threadState.get(), o);
      } else {
        ObjectState os = new ObjectState();
        os.write(ThreadState.threadState.get(), o);
        objects.put(o, os);
      }
    }
  }

  public void storeStatic() {
    Reporter.report.report("Static write of object ");
  }

  public void setThreadStateToActor(Object obj) {
    ThreadState.threadState.set(obj);
  }

  public void setThreadStateToThread() {
    ThreadState.threadState.set(null);
  }

  public void chownObject(Object obj, Object actor) {
    if (objects.containsKey(obj)) {
      objects.get(obj).setOwning(actor);
    } else {
      ObjectState val = new ObjectState();
      val.setOwning(actor);
      objects.put(obj, val);
    }
  }

  WeakIdentityHashMap<Object, ObjectState> objects = new WeakIdentityHashMap<Object, ObjectState>();

}
