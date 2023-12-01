package telran.multithreading.messaging;

import java.util.concurrent.TimeUnit;

public interface MyBlockingQueue<E> {
	boolean add(E e);

	boolean offer(E e);

	void put(E e);

	boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException;

	E take() throws InterruptedException;

	E poll(long timeout, TimeUnit unit) throws InterruptedException;

	E poll();

	E remove();

	E peek();

	E element();

}