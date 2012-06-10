package verityco.inst;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class VClassVisitor extends ClassVisitor {
  public boolean visitingActor = false;
  /*
   * The scala API defines the "receive" is a function that returns a partial
   * function from Any to Nothing. We thus capture classes that are the compiled
   * forms of these functions, and we instrument them appropriately too. :-D
   */
  public boolean visitingReceiveFunction = false;

  public VClassVisitor(ClassVisitor cv) {
    super(Opcodes.ASM4, cv);
  }

  @Override
  public void visit(int version, int access, String name, String signature,
      String superName, String[] interfaces) {
    cv.visit(version, access, name, signature, superName, interfaces);
    if (superName.equals("akka/actor/UntypedActor")) {
      visitingActor = true;
    } else {
      visitingActor = false;
    }

    if (name.contains("$$anonfun$receive$")) {
      visitingReceiveFunction = true;
    } else {
      visitingReceiveFunction = false;
    }
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc,
      String signature, String[] exceptions) {
    // Call down the chain.
    MethodVisitor mv = cv
        .visitMethod(access, name, desc, signature, exceptions);

    if ((name.equals("<init>") && visitingActor) || name.equals("onReceive")
        || (visitingReceiveFunction && name.equals("apply"))) {
      mv = new ThreadStateVisitor(access, name, desc, mv);
    }
    mv = new OwnershipVisitor(mv);
    mv = new LoadStoreVisitor(mv); // Always instrument load/stores last.
    mv = new JavaCoreVisitor(mv); // Always instrument around the Java core
    return mv;
  }
}
