package classloaders;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;

import verityco.logic.Panopticon;
import verityco.util.Reporter;
import atc.TestActorDriver;
import atc.TestActorDriverExtended;
import static org.mockito.Matchers.anyObject;

import static org.mockito.Mockito.*;

public class ClassVisitorTest {
  private static final Set<String> interfaceSet = new HashSet<String>();

  @BeforeClass
  static public void init() throws Exception {
    interfaceSet.add("atc.TestActorDriver");
    interfaceSet.add("atc.TestActorDriverExtended");
  }

  @Test
  public void simpleCase() throws Exception {
    Class<TestActorDriver> cc = loadClass("atc.SimpleCase");
    TestActorDriver testActorDriver = cc.newInstance();
    System.out.println("Testing simple actor message send.");
    testActorDriver.run();
    System.out.println("=====TEST SIMPLE ACTOR DONE=====");
  }

  @Test
  public void basicReporting() throws Exception {
    Reporter r = mock(Reporter.class);
    Reporter.report = r;
    TestActorDriver test = (TestActorDriver) loadClass("atc.BasicReporting")
        .newInstance();
    test.run();

    verify(r).info("Hello world");
  }

  @Test
  public void trivialPanopticon() throws Exception {
    Panopticon p = mock(Panopticon.class);
    Panopticon.panopticon = p;
    TestActorDriver test = (TestActorDriver) loadClass("atc.TrivialPanopticon")
        .newInstance();
    test.run();

    verifyNoMoreInteractions(p);

  }

  @Test
  public void basicActorThreading() throws Exception {
    Panopticon p = mock(Panopticon.class);
    Panopticon.panopticon = p;
    Reporter r = mock(Reporter.class);
    Reporter.report = r;
    TestActorDriver test = (TestActorDriver) loadClass(
        "atc.BasicActorThreading").newInstance();
    test.run();

    InOrder inOrder = inOrder(p, r);
    verify(r, never()).report((String) anyObject());
    inOrder.verify(r).info("Beginning.");
    inOrder.verify(r).info("Created actor system.");
    inOrder.verify(p).chownObject(anyObject(), anyObject());
    inOrder.verify(r).info("Just finished telling.");
    inOrder.verify(p).setThreadStateToActor(anyObject());
    inOrder.verify(r).info("Received.");
    inOrder.verify(p).store(anyObject());
    inOrder.verify(p).setThreadStateToThread();
    inOrder.verify(r).info("Got back: Hi");
    inOrder.verify(r).info("Done.");

  }

  @Test
  public void readWriteTest() throws Exception {
    Panopticon.panopticon = new Panopticon();
    Reporter r = mock(Reporter.class);
    Reporter.report = r;
    // Reporter.report = new Reporter();
    TestActorDriverExtended test = (TestActorDriverExtended) loadClass(
        "atc.ReadWriteActorThreading").newInstance();
    test.run();

    verify(r).reportReadWriteConflict(test.getActorPointer("writer"),
        test.getActorPointer("reader"), test.getMessage("msg"));
  }

  @Test
  public void scalaBasicTest() throws Exception {
    Panopticon p = mock(Panopticon.class);
    Panopticon.panopticon = p;
    Reporter r = mock(Reporter.class);
    Reporter.report = r;
    TestActorDriver test = (TestActorDriver) loadClass("atc.ScalaBasicTest")
        .newInstance();
    test.run();

    InOrder inOrder = inOrder(p, r);
    verify(r, never()).report((String) anyObject());
    inOrder.verify(r).info("Beginning.");

    inOrder.verify(p).chownObject(anyObject(), anyObject());
    inOrder.verify(p, atLeastOnce()).setThreadStateToActor(anyObject());
    inOrder.verify(r).info("Received.");
    inOrder.verify(r).info("hello world 1");
    inOrder.verify(r).info("Done.");
    inOrder.verify(p, atLeastOnce()).setThreadStateToThread();
    inOrder.verify(r).info("Just finished telling, 1.");

    inOrder.verify(r).info("Done with test.");
  }

  @Test
  public void scalaBasicTest2() throws Exception {
    Panopticon p = mock(Panopticon.class);
    Panopticon.panopticon = p;
    Reporter r = mock(Reporter.class);
    Reporter.report = r;
    TestActorDriver test = (TestActorDriver) loadClass("atc.ScalaBasicTest2")
        .newInstance();
    test.run();

    InOrder inOrder = inOrder(p, r);
    verify(r, never()).report((String) anyObject());
    inOrder.verify(r).info("Beginning.");

    inOrder.verify(p).chownObject(anyObject(), anyObject());
    inOrder.verify(p, atLeastOnce()).setThreadStateToActor(anyObject());
    inOrder.verify(r).info("Received.");
    inOrder.verify(r).info("hello world 2");
    inOrder.verify(r).info("Done.");
    inOrder.verify(p, atLeastOnce()).setThreadStateToThread();
    inOrder.verify(r).info("Just finished telling, 2.");

    inOrder.verify(r).info("Done with test.");
  }

  @Test
  public void fullASTest1() throws Exception {
    Panopticon p = mock(Panopticon.class);
    Panopticon.panopticon = p;
    Reporter r = mock(Reporter.class);
    Reporter.report = r;
    TestActorDriver test = (TestActorDriver) loadClass(
        "atc.FullActorSystemTest").newInstance();
    test.run();

    verify(r, never()).report((String) anyObject());
    verify(r, never()).info("Error!");
    verify(r).info("hw1");
    verify(r).info("hw2");
  }

  @SuppressWarnings("rawtypes")
  private Class loadClass(final String className) throws ClassNotFoundException {
    ClassLoader cl = new VerityTestClassLoader(getClass().getClassLoader(),
        interfaceSet);
    return cl.loadClass(className);
  }

}
