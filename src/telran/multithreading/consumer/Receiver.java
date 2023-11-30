package telran.multithreading.consumer;

import java.util.concurrent.locks.ReentrantLock;

import telran.multithreading.messaging.MessageBox;

public class Receiver extends Thread {
	private MessageBox messageBox;

	public Receiver(MessageBox messageBox) {
		setDaemon(true);
		this.messageBox = messageBox;
	}

	@Override
	public void run() {
		while (true) {
			try {

				String message = messageBox.get();
				System.out.printf("Thread %d has got message: %s\n", getId(), message);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}
}
