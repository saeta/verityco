package classloaders;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;

import verityco.logic.Panopticon;
import verityco.util.Reporter;
import atc.TestActorDriver;
import static org.mockito.Matchers.anyObject;

import static org.mockito.Mockito.*;

public class ClassVisitorTest {
  private static final Set<String> interfaceSet = new HashSet<String>();

  @BeforeClass
  static public void init() throws Exception {
    interfaceSet.add("atc.TestActorDriver");
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
    TestActorDriver test = loadClass("atc.BasicReporting").newInstance();
    test.run();

    verify(r).info("Hello world");
  }

  @Test
  public void basicPanopticon() throws Exception {
    Panopticon p = mock(Panopticon.class);
    Panopticon.panopticon = p;
    TestActorDriver test = loadClass("atc.BasicPanopticon").newInstance();
    test.run();

    verify(p).loadStatic(); // Loading of System.out
    verifyNoMoreInteractions(p);

  }

  @Test
  public void basicActorThreading() throws Exception {
    Panopticon p = mock(Panopticon.class);
    Panopticon.panopticon = p;
    Reporter r = mock(Reporter.class);
    Reporter.report = r;
    TestActorDriver test = loadClass("atc.BasicActorThreading").newInstance();
    test.run();

    InOrder inOrder = inOrder(p, r);
    inOrder.verify(r).info("Beginning.");
    inOrder.verify(p).setThreadStateToActor(anyObject());
    inOrder.verify(r).info("Received.");
    inOrder.verify(p).store(anyObject());
    inOrder.verify(p).setThreadStateToThread();
    inOrder.verify(r).info("Got back: Hi");
    inOrder.verify(r).info("Done.");

  }

  @SuppressWarnings("unchecked")
  private Class<TestActorDriver> loadClass(final String className)
      throws ClassNotFoundException {
    ClassLoader cl = new VerityTestClassLoader(getClass().getClassLoader(),
        interfaceSet);
    return (Class<TestActorDriver>) cl.loadClass(className);
  }

}
