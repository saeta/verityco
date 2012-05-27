package verityco.hw;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class HWLoadStoreVisitor extends MethodVisitor {

  public static final void load(Object o) {
    System.out.println("We done loaded " + o);
  }

  public static final void loadStatic() {
    System.out.println("OH SHIT SOMEONE IS STATIC LOADING ");
  }

  public static final void store(Object o) {
    System.out.println("We done storeded " + o);
  }

  public static final void storeStatic() {
    System.out.println("OH SHIT SOMEONE IS STATIC WRITING ");
  }

  public HWLoadStoreVisitor(MethodVisitor mv) {
    super(Opcodes.ASM4, mv);
  }

  @Override
  public void visitFieldInsn(int opcode, String owner, String name, String desc) {
    if (opcode == Opcodes.GETFIELD) {
      mv.visitInsn(Opcodes.DUP);
      mv.visitMethodInsn(Opcodes.INVOKESTATIC,
          "verityco/hw/HWLoadStoreVisitor", "load", "(Ljava/lang/Object;)V");
    } else if (opcode == Opcodes.GETSTATIC) {
      mv.visitMethodInsn(Opcodes.INVOKESTATIC,
          "verityco/hw/HWLoadStoreVisitor", "loadStatic", "()V");
    } else if (opcode == Opcodes.PUTSTATIC) {
      mv.visitMethodInsn(Opcodes.INVOKESTATIC,
          "verityco/hw/HWLoadStoreVisitor", "storeStatic", "()V");
    } else {
      mv.visitInsn(Opcodes.DUP);
      mv.visitMethodInsn(Opcodes.INVOKESTATIC,
          "verityco/hw/HWLoadStoreVisitor", "store", "(Ljava/lang/Object;)V");
    }
    mv.visitFieldInsn(opcode, owner, name, desc);
  }
}
