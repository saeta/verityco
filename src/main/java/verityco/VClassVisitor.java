package verityco;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class VClassVisitor extends ClassVisitor {
  public boolean visitingActor = false;

  public VClassVisitor(ClassVisitor cv) {
    super(Opcodes.ASM4, cv);
  }

  @Override
  public void visit(int version, int access, String name, String signature,
      String superName, String[] interfaces) {
    cv.visit(version, access, name, signature, superName, interfaces);
    if (superName.equals("akka/actor/UntypedActor")) {
      visitingActor = true;
    }
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc,
      String signature, String[] exceptions) {
    MethodVisitor mv = cv
        .visitMethod(access, name, desc, signature, exceptions);
    if (name.equals("<init>") && visitingActor) {

      mv = new ThreadStateVisitor(access, name, desc, mv);
    } else if (name.equals("onReceive")) {
      mv = new ThreadStateVisitor(access, name, desc, mv);
      mv = new OwnershipVisitor(access, name, desc, mv);
    }

    mv = new LoadStoreVisitor(mv); // Always instrument load/stores last.
    mv = new JavaCoreVisitor(mv); // Always instrument around the Java core
    return mv;
  }
}
