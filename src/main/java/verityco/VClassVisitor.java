package verityco;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class VClassVisitor extends ClassVisitor {

  public VClassVisitor(ClassVisitor cv) {
    super(Opcodes.ASM4, cv);
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc,
      String signature, String[] exceptions) {
    MethodVisitor mv = cv
        .visitMethod(access, name, desc, signature, exceptions);

    if (name.equals("onReceive")) {
      // mv = // TODO: instrument bytecode.
    }
    mv = new LoadStoreVisitor(mv); // Always instrument load/stores last.
    return mv;
  }
}
