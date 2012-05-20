package verityco;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class ThreadStateVisitor extends AdviceAdapter {
	
	public static void setThreadStateToActor(Object obj) {
		System.out.println("We are in an actor method.");
	}

	public static void setThreadStateToThread() {
		System.out.println("We are leaving an actor method.");
	}
	
	public ThreadStateVisitor(int access, String name, String desc,
			MethodVisitor mv) {
		super(ASM4, mv, access, name, desc);
	}

	
	public void onMethodEnter() {
		mv.visitVarInsn(Opcodes.ALOAD,0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "verityco/ThreadStateVisitor", "setThreadStateToActor", "(Ljava/lang/Object;)V");
	}
	
	public void onMethodExit(int opcode) {
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "verityco/ThreadStateVisitor", "setThreadStateToThread", "()V");
	}
}
