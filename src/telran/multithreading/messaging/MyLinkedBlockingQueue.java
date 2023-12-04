package telran.multithreading.messaging;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class MyLinkedBlockingQueue<E> implements MyBlockingQueue<E> {
	int limit;
	private LinkedList<E> queue = new LinkedList<>();
	private ReentrantLock lock = new ReentrantLock();
	private Condition waitingForConsuming = lock.newCondition();
	private Condition waitingForProducing = lock.newCondition();

	public MyLinkedBlockingQueue(int limit) {
		super();
		this.limit = limit;
	}

	@Override
	public boolean add(E e) {
		try {
			lock.lock();
			if (queue.size() == limit) {
				throw new IllegalStateException();
			}
			Boolean res = queue.add(e);
			waitingForConsuming.signal();
			return res;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean offer(E e) {
		boolean res = false;
		try {
			lock.lock();
			if (queue.size() == limit) {
				throw new IllegalArgumentException();
			} else {
				res = queue.add(e);
				waitingForConsuming.signal();
			}
		} finally {
			lock.unlock();
		}
		return res;
	}

	@Override
	public void put(E e) throws InterruptedException {
		try {
			lock.lock();
			while (queue.size() == limit) {
				waitingForProducing.await();
			}
			queue.add(e);
			waitingForConsuming.signal();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		boolean res = false;
		try {
			lock.lock();
			if (waitingForConsuming.await(timeout, unit)) {
				res = queue.add(e);
			}
		} finally {
			lock.unlock();
		}
		return res;
	}

	@Override
	public E take() throws InterruptedException {
		try {
			lock.lock();
			while (queue.isEmpty()) {
				waitingForConsuming.await();
			}
			E element = queue.remove();
			waitingForProducing.signal();
			return element;
		} finally {
			lock.unlock();
		}

	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		E elem = null;
		try {
			lock.lock();
			if (waitingForConsuming.await(timeout, unit)) {
				elem = queue.remove();
			}
		} finally {
			lock.unlock();
		}
		return elem;
	}

	@Override
	public E remove() {
		try {
			lock.lock();
			if (queue.isEmpty()) {
				throw new NoSuchElementException();
			}
			E removeMessage = queue.remove();
			waitingForProducing.signal();
			return removeMessage;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E peek() {
		E elem = null;
		try {
			lock.lock();
			if (!queue.isEmpty()) {
				elem = queue.getFirst();
				waitingForConsuming.signal();
			}
		} finally {
			lock.unlock();
		}
		return elem;
	}

	@Override
	public E element() {
		try {
			lock.lock();
			if (queue.isEmpty()) {
				throw new NoSuchElementException();
			}
			E element = queue.getFirst();
			waitingForConsuming.signal();
			return element;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E poll() {
		E element = null;
		try {
			lock.lock();
			if (!queue.isEmpty()) {
				element = queue.remove();
				waitingForConsuming.signal();
			}
		} finally {
			lock.unlock();
		}
		return element;
	}

}