package telran.multithreading;

import java.util.concurrent.*;

public class ImitatorAppl {

	private static final int N_REQUESTS = 1000000;
	private static final long RUNNING_TIME = 100;//if do 1 all number of processed requests

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Request> queue = new LinkedBlockingQueue<>();
		ClientImitator client = new ClientImitator(N_REQUESTS, RUNNING_TIME, queue);
		ServerImitator server = new ServerImitator(queue);
		client.start();
		server.start();
		client.join();

		server.interrupt();
		server.shutdown();
		System.out.printf("number of all requests %d\n", N_REQUESTS);
		System.out.printf("number of processed requests %d\n", Request.getActualRequestAmount());
	}

}