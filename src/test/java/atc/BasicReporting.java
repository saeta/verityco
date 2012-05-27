package atc;

import verityco.util.Reporter;

public class BasicReporting implements TestActorDriver {

  @Override
  public void run() {
    Reporter.report.info("Hello world");
  }
}
