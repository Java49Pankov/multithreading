package telran.multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class Request implements Runnable {
	long runningTime;
	static AtomicInteger counter = new AtomicInteger(0);

	public Request(long runningTime) {
		this.runningTime = runningTime;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(runningTime);// Imitation of request processing
			counter.incrementAndGet();
		} catch (InterruptedException e) {

		}
	}

	static public int getActualRequestAmount() {
		return counter.get();
	}

}