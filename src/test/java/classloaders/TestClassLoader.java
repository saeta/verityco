package classloaders;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import verityco.hw.HWClassVisitor;

public class TestClassLoader extends ClassLoader {
  private final String className;
  private final ClassLoader cl;

  public TestClassLoader(ClassLoader cl, String classname) {
    super();
    this.cl = cl;
    this.className = classname;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Class loadClass(String name) throws ClassNotFoundException {
    if (className.equals(name)) {
      try {
        byte[] bytecode = transformClass(className);
        return super.defineClass(className, bytecode, 0, bytecode.length);

      } catch (IOException ex) {
        throw new ClassNotFoundException("Load error: " + ex.toString(), ex);

      }
    }
    return cl.loadClass(name);
  }

  private byte[] transformClass(String className) throws IOException {
    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    HWClassVisitor ncv = new HWClassVisitor(cw);

    ClassReader cr = new ClassReader(getClass().getResourceAsStream(
        "/" + className.replace('.', '/') + ".class"));
    cr.accept(ncv, ClassReader.SKIP_FRAMES);
    return cw.toByteArray();
  }

}
