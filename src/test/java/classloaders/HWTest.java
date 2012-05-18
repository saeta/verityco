package classloaders;

import org.junit.Before;
import org.junit.Test;

import verityco.Foo;

public class HWTest {

  private static final String TEST_CLASS = "verityco.FooImpl";

  private Foo foo;

  @Before
  public void setUp() throws Exception {
    Class cc = loadClass(TEST_CLASS);
    foo = (Foo) cc.newInstance();
  }

  @Test
  public void test() {
    foo.foo();
  }

  private Class loadClass(final String className) throws ClassNotFoundException {
    ClassLoader cl = new TestClassLoader(getClass().getClassLoader(), className);
    return cl.loadClass(className);
  }

}
