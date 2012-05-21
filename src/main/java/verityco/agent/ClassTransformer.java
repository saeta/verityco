package verityco.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import verityco.VClassVisitor;

public class ClassTransformer implements ClassFileTransformer {

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		VClassVisitor vcv = new VClassVisitor(cw);

		ClassReader cr = new ClassReader(classfileBuffer);
		cr.accept(vcv, ClassReader.SKIP_FRAMES);
		return cw.toByteArray();
	}

}
