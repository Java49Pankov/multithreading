package telran.multithreading;

import java.util.concurrent.*;

public class ClientImitator extends Thread {
	int nRequests;
	long runningTime;
	BlockingQueue<Request> queue;

	public ClientImitator(int nRequests, long runningTime, BlockingQueue<Request> queue) {
		this.nRequests = nRequests;
		this.runningTime = runningTime;
		this.queue = queue;
	}

	@Override
	public void run() {
		for (int i = 0; i < nRequests; i++) {
			try {
				queue.put(new Request(runningTime));
			} catch (InterruptedException e) {
				break;
			}
		}
	}

}