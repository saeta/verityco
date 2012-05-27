package verityco.agent;

import java.lang.instrument.Instrumentation;

public class ClassProcessor {

  public static void premain(String agentArgs, Instrumentation inst) {
    ClassTransformer ct = new ClassTransformer();
    inst.addTransformer(ct, true);
  }
}
