package verityco;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

public class HWMethodVisitor extends AdviceAdapter {

  public static final void foo() {
    System.out.println("Hello world!");
  }

  public HWMethodVisitor(int access, String name, String desc, MethodVisitor mv) {
    super(ASM4, mv, access, name, desc);
  }

  @Override
  protected void onMethodEnter() {
    mv.visitMethodInsn(INVOKESTATIC, "verityco/HWMethodVisitor", "foo", "()V");
  }
}
