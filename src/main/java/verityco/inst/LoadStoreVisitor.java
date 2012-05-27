package verityco.inst;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import verityco.util.Reporter;

public class LoadStoreVisitor extends MethodVisitor {

  public static final void load(Object o) {
    Reporter.report.report("Loading object " + o);
  }

  public static final void loadStatic() {
    Reporter.report.report("Static load of object ");
  }

  public static final void store(Object o) {
    Reporter.report.report("Storing object " + o);
  }

  public static final void storeStatic() {
    Reporter.report.report("Static write of object ");
  }

  public LoadStoreVisitor(MethodVisitor mv) {
    super(Opcodes.ASM4, mv);
  }

  @Override
  public void visitFieldInsn(int opcode, String owner, String name, String desc) {
    if (opcode == Opcodes.GETFIELD) {
      mv.visitInsn(Opcodes.DUP);
      mv.visitMethodInsn(Opcodes.INVOKESTATIC,
          "verityco/inst/LoadStoreVisitor", "load", "(Ljava/lang/Object;)V");
    } else if (opcode == Opcodes.GETSTATIC) {
      mv.visitMethodInsn(Opcodes.INVOKESTATIC,
          "verityco/inst/LoadStoreVisitor", "loadStatic", "()V");
    } else if (opcode == Opcodes.PUTSTATIC) {
      mv.visitMethodInsn(Opcodes.INVOKESTATIC,
          "verityco/inst/LoadStoreVisitor", "storeStatic", "()V");
    } else {
      mv.visitInsn(Opcodes.SWAP);
      mv.visitInsn(Opcodes.DUP);
      mv.visitMethodInsn(Opcodes.INVOKESTATIC,
          "verityco/inst/LoadStoreVisitor", "store", "(Ljava/lang/Object;)V");
      mv.visitInsn(Opcodes.SWAP);
    }
    mv.visitFieldInsn(opcode, owner, name, desc);
  }

}
