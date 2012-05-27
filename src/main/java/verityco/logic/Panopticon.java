package verityco.logic;

import verityco.util.Reporter;

public class Panopticon {
  public static Panopticon panopticon = new Panopticon();

  public void load(Object o) {
    Reporter.report.report("Loading object " + o);
  }

  public void loadStatic() {
    Reporter.report.report("Static load of object ");
  }

  public void store(Object o) {
    Reporter.report.report("Storing object " + o);
  }

  public void storeStatic() {
    Reporter.report.report("Static write of object ");
  }

  public void setThreadStateToActor(Object obj) {
    Reporter.report.report("We are in an actor method.");
  }

  public void setThreadStateToThread() {
    Reporter.report.report("We are leaving an actor method.");
  }
}
