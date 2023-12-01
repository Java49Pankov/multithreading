package telran.multithreading.messaging;

import java.util.concurrent.locks.*;

public class MessageBox {
	private String message;
	private ReentrantLock lock = new ReentrantLock();
	private Condition waitingForConsuming = lock.newCondition();
	private Condition waitingForProducing = lock.newCondition();

	public void put(String message) throws InterruptedException {
		try {
			lock.lock();
			while (this.message != null) {
				waitingForProducing.await();
			}
			this.message = message;
			waitingForConsuming.signal();
		} finally {
			lock.unlock();
		}
	}

	public String get() throws InterruptedException {
		try {
			lock.lock();
			while (message == null) {
				waitingForConsuming.await();
			}
			String res = message;
			message = null;
			waitingForProducing.signal();
			return res;
		} finally {
			lock.unlock();
		}
	}

	public String take() {
		return message;
	}
}
