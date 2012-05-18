package verityco.hw;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class HWClassVisitor extends ClassVisitor {

  public HWClassVisitor(ClassVisitor cv) {
    super(Opcodes.ASM4, cv);
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc,
      String signature, String[] exceptions) {
    MethodVisitor mv = cv
        .visitMethod(access, name, desc, signature, exceptions);
    // Chaining shit
    MethodVisitor mv2 = new HWMethodVisitor(access, name, desc, mv);
    return new HWLoadStoreVisitor(mv2);
  }

}
