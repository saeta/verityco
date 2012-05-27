package classloaders;

import java.io.IOException;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import verityco.VClassVisitor;

public class VerityTestClassLoader extends ClassLoader {
  private final String className;
  private final ClassLoader cl;
  private final Set<String> interfaces;

  public VerityTestClassLoader(ClassLoader cl, String classname,
      Set<String> interfaces) {
    super();
    this.cl = cl;
    this.className = classname;
    this.interfaces = interfaces;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Class loadClass(String name) throws ClassNotFoundException {
    if ((name.startsWith("atc") && !interfaces.contains(name))
        || (name.equals("java.util.HashMap"))) {
      try {
        byte[] bytecode = transformClass(name);
        return super.defineClass(name, bytecode, 0, bytecode.length);

      } catch (IOException ex) {
        throw new ClassNotFoundException("Load error: " + ex.toString(), ex);

      }
    }
    return cl.loadClass(name);
  }

  private byte[] transformClass(String className) throws IOException {
    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    VClassVisitor vcv = new VClassVisitor(cw);

    ClassReader cr = new ClassReader(getClass().getResourceAsStream(
        "/" + className.replace('.', '/') + ".class"));
    cr.accept(vcv, ClassReader.SKIP_FRAMES);
    return cw.toByteArray();
  }

}
