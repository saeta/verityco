package verityco;

public class FooWithVarsImpl implements FooWithVars {

  public static class Var {
    public Integer a;
    public Integer b;

    public Var() {
      a = new Integer(2);
      b = new Integer(1);
    }

  }

  static Var staticVar = new Var();

  @Override
  public void foo() {
    Var varz = new Var();
    // Load
    varz.a = 2;
    // Load and Store
    varz.b = varz.a;
    bar();
  }

  @Override
  public void bar() {
    Var varzBar = new Var();
    // 2 stores and load
    varzBar.a = 42;
    varzBar.b = 45;
    int hello = varzBar.a + varzBar.b;
    baz();
  }

  @Override
  public void baz() {
    // Load Store
    staticVar.a = staticVar.b + 1000000000;
  }
}
