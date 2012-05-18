package verityco;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

public class OwnershipVisitor extends AdviceAdapter {
  public OwnershipVisitor(int access, String name, String desc, MethodVisitor mv) {
    super(ASM4, mv, access, name, desc);
  }
}
