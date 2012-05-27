package classloaders;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import atc.TestActorDriver;

public class ClassVisitorTest {
  private static final String TEST_CLASS = "atc.TestActorDriverImpl";

  private static final Set<String> interfaceSet = new HashSet<String>();

  private TestActorDriver testActorDriver;

  @SuppressWarnings("unchecked")
  @Before
  public void init() throws Exception {
    interfaceSet.add("atc.TestActorDriver");
    Class<TestActorDriver> cc = loadClass(TEST_CLASS);
    testActorDriver = cc.newInstance();
  }

  @Test
  public void simpleCase() {
    System.out.println("Testing simple actor message send.");
    testActorDriver.run();
    System.out.println("=====TEST SIMPLE ACTOR DONE=====");
  }

  private Class loadClass(final String className) throws ClassNotFoundException {
    ClassLoader cl = new VerityTestClassLoader(getClass().getClassLoader(),
        className, interfaceSet);
    return cl.loadClass(className);
  }

}
