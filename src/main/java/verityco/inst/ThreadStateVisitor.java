package verityco.inst;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

import verityco.util.Reporter;

public class ThreadStateVisitor extends AdviceAdapter {

  public static void setThreadStateToActor(Object obj) {
    Reporter.report.report("We are in an actor method.");
  }

  public static void setThreadStateToThread() {
    Reporter.report.report("We are leaving an actor method.");
  }

  public ThreadStateVisitor(int access, String name, String desc,
      MethodVisitor mv) {
    super(ASM4, mv, access, name, desc);
  }

  @Override
  public void onMethodEnter() {
    mv.visitVarInsn(Opcodes.ALOAD, 0);
    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
        "verityco/inst/ThreadStateVisitor", "setThreadStateToActor",
        "(Ljava/lang/Object;)V");
  }

  @Override
  public void onMethodExit(int opcode) {
    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
        "verityco/inst/ThreadStateVisitor", "setThreadStateToThread", "()V");
  }
}
