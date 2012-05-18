package verityco;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

public class ThreadStateVisitor extends AdviceAdapter {
  public ThreadStateVisitor(int access, String name, String desc,
      MethodVisitor mv) {
    super(ASM4, mv, access, name, desc);
  }
}
