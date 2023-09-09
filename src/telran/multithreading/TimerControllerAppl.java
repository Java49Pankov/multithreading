package telran.multithreading;

public class TimerControllerAppl {

	public static void main(String[] args) throws InterruptedException {
		Timer timer = new Timer();
		timer.start();
		// Doing something in the application;
		Thread.sleep(5000);

	}

}
