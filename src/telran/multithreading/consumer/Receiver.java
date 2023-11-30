package telran.multithreading.consumer;

import telran.multithreading.messaging.MessageBox;

public class Receiver extends Thread {
	private MessageBox messageBox;

	public Receiver(MessageBox messageBox) {
		this.messageBox = messageBox;
	}

	@Override
	public void run() {
		String message = null;
		try {
			while (true) {
				message = messageBox.get();
				messageProcessing(message);
			}
		} catch (InterruptedException e) {
			message = messageBox.take();
			while (message != null) {
				messageProcessing(message);
			}
		}
	}

	private void messageProcessing(String message) {
		System.out.printf("Thread %d has got message: %s\n", getId(), message);
	}
}
