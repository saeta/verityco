package classloaders;

import org.junit.Before;
import org.junit.Test;

import verityco.Foo;
import verityco.FooWithVars;

public class HWTest {

  private static final String TEST_CLASS = "verityco.FooImpl";
  private static final String TEST_CLASS2 = "verityco.FooWithVarsImpl";

  private Foo foo;
  private FooWithVars fooWithVars;

  @Before
  public void setUp() throws Exception {
    Class cc = loadClass(TEST_CLASS);
    Class cc2 = loadClass(TEST_CLASS2);
    foo = (Foo) cc.newInstance();
    fooWithVars = (FooWithVars) cc2.newInstance();
  }

  @Test
  public void test() {
    System.out.println("WE DONE GONNA DO TEST 1");
    foo.foo();
  }

  @Test
  public void test2() {
    System.out.println("WE DONE GONNA DO TEST 2");
    fooWithVars.foo();
  }

  private Class loadClass(final String className) throws ClassNotFoundException {
    ClassLoader cl = new TestClassLoader(getClass().getClassLoader(), className);
    return cl.loadClass(className);
  }

}
