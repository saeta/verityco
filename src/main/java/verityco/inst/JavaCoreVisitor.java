package verityco.inst;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class JavaCoreVisitor extends MethodVisitor {

  public static final Map<String, Set<String>> CORE_METHODS = new HashMap<String, Set<String>>();

  static {
    Set<String> listWritingMethods = new HashSet<String>();
    listWritingMethods.add("add");
    listWritingMethods.add("addAll");
    listWritingMethods.add("clear");
    listWritingMethods.add("remove");
    listWritingMethods.add("removeAll");
    listWritingMethods.add("retainAll");
    listWritingMethods.add("set");
    CORE_METHODS.put("java/util/List", listWritingMethods);

    Set<String> mapWritingMethods = new HashSet<String>();
    mapWritingMethods.add("clear");
    mapWritingMethods.add("put");
    mapWritingMethods.add("putAll");
    mapWritingMethods.add("remove");
    CORE_METHODS.put("java/util/Map", mapWritingMethods);

    Set<String> setWritingMethods = new HashSet<String>();
    setWritingMethods.add("add");
    setWritingMethods.add("addAll");
    setWritingMethods.add("clear");
    setWritingMethods.add("remove");
    setWritingMethods.add("removeAll");
    setWritingMethods.add("retainAll");
    CORE_METHODS.put("java/util/Set", setWritingMethods);
  }

  public JavaCoreVisitor(MethodVisitor mv) {
    super(Opcodes.ASM4, mv);
  }

  @Override
  public void visitMethodInsn(int opcode, String owner, String name, String desc) {
    if (opcode == Opcodes.INVOKEVIRTUAL || opcode == Opcodes.INVOKEINTERFACE) {
      if (CORE_METHODS.containsKey(owner)) {
        String method = CORE_METHODS.get(owner).contains(name) ? "store"
            : "load";
        Type[] types = Type.getArgumentTypes(desc);

        if (types.length == 1) {
          mv.visitInsn(Opcodes.DUP2);
          mv.visitInsn(Opcodes.POP);
          mv.visitMethodInsn(Opcodes.INVOKESTATIC,
              "verityco/inst/LoadStoreVisitor", method, "(Ljava/lang/Object;)V");
        } else if (types.length == 2) {
          mv.visitInsn(Opcodes.DUP2_X1);
          mv.visitInsn(Opcodes.POP2);
          mv.visitInsn(Opcodes.DUP_X2);
          mv.visitMethodInsn(Opcodes.INVOKESTATIC,
              "verityco/inst/LoadStoreVisitor", method, "(Ljava/lang/Object;)V");
        } else if (types.length == 0) {
          mv.visitInsn(Opcodes.DUP);
          mv.visitMethodInsn(Opcodes.INVOKESTATIC,
              "verityco/inst/LoadStoreVisitor", method, "(Ljava/lang/Object;)V");
        } else {
          System.out.println("Help!!!! I couldn't instrument this method!");
        }
      }
    }
    mv.visitMethodInsn(opcode, owner, name, desc);
  }
}
