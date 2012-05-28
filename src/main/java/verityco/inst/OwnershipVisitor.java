package verityco.inst;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import verityco.logic.Panopticon;

public class OwnershipVisitor extends MethodVisitor {

  public static void toPanopticon(Object one, Object two) {
    Panopticon.panopticon.chownObject(one, two);
  }

  public OwnershipVisitor(MethodVisitor mv) {
    super(Opcodes.ASM4, mv);
  }

  @Override
  public void visitMethodInsn(int opcode, String owner, String name, String desc) {
    if ((owner.equals("akka/actor/ActorRef") || owner
        .equals("akka/testkit/TestActorRef")) && name.equals("tell")) {
      mv.visitInsn(Opcodes.DUP2);
      mv.visitMethodInsn(Opcodes.INVOKESTATIC,
          "verityco/inst/OwnershipVisitor", "toPanopticon",
          "(Ljava/lang/Object;Ljava/lang/Object;)V");
    }
    mv.visitMethodInsn(opcode, owner, name, desc);
  }

}
