package verityco.util;

public class Reporter {

  public static Reporter report = new Reporter();

  public void report(String error) {
    System.out.println(error);
  }

  public void info(String informational) {
    System.out.println(informational);
  }

}
