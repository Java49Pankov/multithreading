package telran.multithreading;

import telran.multithreading.consumer.Receiver;
import telran.multithreading.messaging.MessageBox;
import telran.multithreading.producer.Sender;

public class SenderReceiverAppl {

	private static final int N_MESSAGES = 20;
	private static final int N_RECEIVERS = 10;

	public static void main(String[] args) throws InterruptedException {
		MessageBox messageBoxOdd = new MessageBox();
		MessageBox messageBoxEven = new MessageBox();

		Sender sender = new Sender(messageBoxOdd, messageBoxEven, N_MESSAGES);
		sender.start();
		startReceivers(messageBoxOdd, messageBoxEven);
		Thread.sleep(200);
	}

	private static void startReceivers(MessageBox messageBoxOdd, MessageBox messageBoxEven) {
		for (int i = 0; i < N_RECEIVERS; i++) {
			Receiver receiver = new Receiver();
			MessageBox messageBox = receiver.getId() % 2 == 0 ? messageBoxOdd : messageBoxEven;
			receiver.setMessageBox(messageBox);
			receiver.start();
		}
	}

}
