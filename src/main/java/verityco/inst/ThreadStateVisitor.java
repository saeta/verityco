package verityco.inst;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class ThreadStateVisitor extends AdviceAdapter {

  public ThreadStateVisitor(int access, String name, String desc,
      MethodVisitor mv) {
    super(ASM4, mv, access, name, desc);
  }

  @Override
  public void onMethodEnter() {
    mv.visitFieldInsn(Opcodes.GETSTATIC, "verityco/logic/Panopticon",
        "panopticon", "Lverityco/logic/Panopticon;");
    mv.visitVarInsn(Opcodes.ALOAD, 0);
    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "verityco/logic/Panopticon",
        "setThreadStateToActor", "(Ljava/lang/Object;)V");
  }

  @Override
  public void onMethodExit(int opcode) {
    mv.visitFieldInsn(Opcodes.GETSTATIC, "verityco/logic/Panopticon",
        "panopticon", "Lverityco/logic/Panopticon;");
    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "verityco/logic/Panopticon",
        "setThreadStateToThread", "()V");
  }
}
