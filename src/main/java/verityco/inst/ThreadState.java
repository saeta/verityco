package verityco.inst;

public class ThreadState {

  public static final ThreadLocal<Object> threadState = new ThreadLocal<Object>() {

    @Override
    protected Object initialValue() {
      return null;
    }
  };

}
