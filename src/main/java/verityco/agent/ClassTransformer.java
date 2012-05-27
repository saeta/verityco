package verityco.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import verityco.inst.VClassVisitor;

public class ClassTransformer implements ClassFileTransformer {

  /**
   * Entry point into the class file transformer.
   * 
   * Chooses to either not transform the class (by returning the classfileBuffer
   * or transforms the class by setting up the ASM instrumentation chain, and
   * returning those results.
   */
  @Override
  public byte[] transform(ClassLoader loader, String className,
      Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
      byte[] classfileBuffer) throws IllegalClassFormatException {
    // Ignore ourselves.
    if (className.startsWith("verityco/") || className.startsWith("java/")) {
      return classfileBuffer;
    }

    // Transform the class
    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    System.out.println("Instrumenting class: " + className);
    VClassVisitor vcv = new VClassVisitor(cw);

    ClassReader cr = new ClassReader(classfileBuffer);
    cr.accept(vcv, ClassReader.SKIP_FRAMES);
    return cw.toByteArray();
  }

}
