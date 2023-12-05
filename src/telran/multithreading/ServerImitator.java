package telran.multithreading;

import java.util.concurrent.*;

public class ServerImitator extends Thread {
	BlockingQueue<Request> queue;
	ExecutorService threadPool = Executors.newFixedThreadPool(100);

	public ServerImitator(BlockingQueue<Request> queue) {
		this.queue = queue;
	}

	public void shutdown() throws InterruptedException {
		threadPool.awaitTermination(20, TimeUnit.SECONDS);
		threadPool.shutdownNow();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Request request = queue.take();
				threadPool.execute(request);
			} catch (InterruptedException e) {
				Request remainedReq = null;
				while ((remainedReq = queue.poll()) != null) {
					threadPool.execute(remainedReq);
				}
				break;
			}
		}
	}
}