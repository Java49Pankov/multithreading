package telran.multithreading.messaging;

public class MessageBox {
	private String message;

	public synchronized void put(String message) throws InterruptedException {
		while (this.message != null) {
			this.wait();
		}
		this.message = message;
		this.notifyAll();
	}

	public synchronized String get() throws InterruptedException {
		while (message == null) {
			this.wait();
		}
		String res = message;
		message = null;
		this.notifyAll();
		return res;
	}

	public String take() {
		return message;
	}
}
