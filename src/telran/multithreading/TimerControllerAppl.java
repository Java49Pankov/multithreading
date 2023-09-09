package telran.multithreading;

public class TimerControllerAppl {

	public static void main(String[] args) throws InterruptedException {
		Timer timer = new Timer();
		timer.start();
		System.out.println(" Doing something in the application with timer");
		Thread.sleep(5000);
		timer.interrupt();
		System.out.println(" Doing something in the application with out timer");
		Thread.sleep(5000);
	}

}
