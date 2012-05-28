package verityco.inst;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LoadStoreVisitor extends MethodVisitor {

  public LoadStoreVisitor(MethodVisitor mv) {
    super(Opcodes.ASM4, mv);
  }

  @Override
  public void visitFieldInsn(int opcode, String owner, String name, String desc) {
    if (opcode == Opcodes.GETFIELD) {
      // GETFIELD
      mv.visitFieldInsn(Opcodes.GETSTATIC, "verityco/logic/Panopticon",
          "panopticon", "Lverityco/logic/Panopticon;");
      mv.visitInsn(Opcodes.DUP);
      mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "verityco/logic/Panopticon",
          "load", "(Ljava/lang/Object;)V");
    } else if (opcode == Opcodes.PUTFIELD) {
      // PUTFIELD
      mv.visitInsn(Opcodes.SWAP);
      mv.visitInsn(Opcodes.DUP);
      mv.visitFieldInsn(Opcodes.GETSTATIC, "verityco/logic/Panopticon",
          "panopticon", "Lverityco/logic/Panopticon;");
      mv.visitInsn(Opcodes.SWAP);
      mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "verityco/logic/Panopticon",
          "store", "(Ljava/lang/Object;)V");
      mv.visitInsn(Opcodes.SWAP);
    }
    // Do the operation
    mv.visitFieldInsn(opcode, owner, name, desc);
  }
}
