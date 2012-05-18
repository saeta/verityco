package verityco.hw;

public class FooImpl implements Foo {

  /* (non-Javadoc)
   * @see verityco.Foo#foo()
   */
  @Override
  public void foo() {
    System.out.println("FOO!");
    bar();
  }

  /* (non-Javadoc)
   * @see verityco.Foo#bar()
   */
  @Override
  public void bar() {
    System.out.println("BAR!");
    baz();
  }

  /* (non-Javadoc)
   * @see verityco.Foo#baz()
   */
  @Override
  public void baz() {
    System.out.println("BAZ!");
  }

}
