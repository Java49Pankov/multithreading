package telran.multithreading.consumer;

import telran.multithreading.messaging.MessageBox;

public class Receiver extends Thread {
	private MessageBox messageBox;

	public MessageBox getMessageBox() {
		return messageBox;
	}

	public void setMessageBox(MessageBox messageBox) {
		this.messageBox = messageBox;
	}

	public Receiver(MessageBox messageBox) {
		setDaemon(true); // FIXME
		this.messageBox = messageBox;
	}

	public Receiver() {
		this(null);
	}

	@Override
	public void run() {
		while (true) {// FIXME
			try {
				String message = messageBox.get();
				System.out.printf("Thread %d has got message: %s\n", getId(), message);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}