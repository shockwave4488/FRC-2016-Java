import java.lang.Runnable;
import java.lang.Thread;

public class TimeoutInvoke {
	
	public interface Func {
		Object run(Object arg);
	}
	
	public static boolean TryExecute(Runnable expression, long timeoutMs) throws InterruptedException{
		Thread t = new Thread(expression);
		t.start();
		
		t.join(timeoutMs);
		if(t.isAlive()){
			t.interrupt();
			return false;
		}
		
		return true;
	}
	
	public static Object TryExecute(Func expression, Object param, long timeoutMs) throws InterruptedException{
		Object[] toReturn = new Object[1];
		Runnable r = new Runnable() {
			@Override
			public void run(){
				toReturn[0] = expression.run(param);
			}
		};
		
		TryExecute(r, timeoutMs);
		return toReturn[0];
	}
}
