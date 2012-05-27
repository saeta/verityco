package verityco;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class OwnershipVisitor extends AdviceAdapter {

  public static void setOwnership(Object actor, Object item) {
    System.out.println("We are ownering " + item + " with acto " + actor);
  }

  public OwnershipVisitor(int access, String name, String desc, MethodVisitor mv) {
    super(ASM4, mv, access, name, desc);
  }

  @Override
  public void onMethodEnter() {
    mv.visitVarInsn(Opcodes.ALOAD, 0);
    mv.visitVarInsn(Opcodes.ALOAD, 1);
    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "verityco/OwnershipVisitor",
        "setOwnership", "(Ljava/lang/Object;Ljava/lang/Object;)V");
  }
}
