package telran.multithreading;

public class FakedDeadLock {

	public static void main(String[] args) throws InterruptedException {
		Thread.currentThread().join();
	}

}
