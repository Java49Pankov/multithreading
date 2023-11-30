package telran.multithreading.producer;

import telran.multithreading.messaging.MessageBox;

public class Sender extends Thread {
	private MessageBox messageBoxOdd;
	private MessageBox messageBoxEven;
	private int nMessages;

	public Sender(MessageBox messageBoxOdd, MessageBox messageBoxEven, int nMessages) {
		this.messageBoxOdd = messageBoxOdd;
		this.messageBoxEven = messageBoxEven;
		this.nMessages = nMessages;
	}

	@Override
	public void run() {
		MessageBox messageBox = null;
		for (int i = 1; i <= nMessages; i++) {
			try {
				messageBox = i % 2 == 0 ? messageBoxOdd : messageBoxEven;
				messageBox.put("message" + i);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

}