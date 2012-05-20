package classloaders;

import org.junit.Before;
import org.junit.Test;

import verityco.hw.Foo;
import verityco.hw.FooWithVars;

public class HWTest {

  private static final String TEST_CLASS = "verityco.hw.FooImpl";
  private static final String TEST_CLASS2 = "verityco.hw.FooWithVarsImpl";

  private Foo foo;
  private FooWithVars fooWithVars;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    Class<Foo> cc = loadClass(TEST_CLASS);
    Class<FooWithVars> cc2 = loadClass(TEST_CLASS2);
    foo = cc.newInstance();
    fooWithVars = cc2.newInstance();
  }

  public void test() {
    System.out.println("WE DONE GONNA DO TEST 1");
    foo.foo();
    System.out.println("=====TEST 1 DONE=====");
  }

  public void test2() {
    System.out.println("WE DONE GONNA DO TEST 2");
    fooWithVars.foo();
    System.out.println("=====TEST 2 DONE=====");
  }

  @SuppressWarnings("rawtypes")
  private Class loadClass(final String className) throws ClassNotFoundException {
    ClassLoader cl = new TestClassLoader(getClass().getClassLoader(), className);
    return cl.loadClass(className);
  }

}
