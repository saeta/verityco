package verityco.util;

import java.io.PrintStream;

public class Reporter {

  public static Reporter report = new Reporter(null);

  final private PrintStream ps;

  public Reporter(PrintStream testPs) {
    ps = testPs;
  }

  public void report(String error) {
    if (ps != null) {
      ps.println(error);
    }
    System.out.println(error);
  }

  public PrintStream getPrintStream() {
    return ps;
  }

}
