package telran.multithreading;

import telran.multithreading.consumer.Receiver;
import telran.multithreading.messaging.MessageBox;
import telran.multithreading.producer.Sender;

public class SenderReceiverAppl {

	private static final int N_MESSAGES = 20;
	private static final int N_RECEIVERS = 20;

	public static void main(String[] args) throws InterruptedException {
		MessageBox messageBox = new MessageBox();
		Sender sender = new Sender(messageBox, N_MESSAGES);
		sender.start();
		Receiver[] receivers = new Receiver[N_RECEIVERS];
		startReceivers(messageBox, receivers);
		sender.join();
		stopReceivers(receivers);
	}

	private static void startReceivers(MessageBox messageBox, Receiver[] receivers) {
		for (int i = 0; i < N_RECEIVERS; i++) {
			receivers[i] = new Receiver(messageBox);
			receivers[i].start();
		}
	}

	private static void stopReceivers(Receiver[] receivers) {
		for (Receiver recever : receivers) {
			recever.interrupt();
		}
	}

}
