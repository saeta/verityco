package verityco.agent;

import java.lang.instrument.Instrumentation;

public class ClassProcessor {

	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("SHIT FUCK");
		inst.addTransformer(new ClassTransformer());
	}
}
