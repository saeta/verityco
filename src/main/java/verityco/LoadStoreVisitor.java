package verityco;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LoadStoreVisitor extends MethodVisitor {

  public LoadStoreVisitor(MethodVisitor mv) {
    super(Opcodes.ASM4, mv);
  }

}
