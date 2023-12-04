package telran.multithreading.messaging;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class MyLinkedBlockingQueue<E> implements MyBlockingQueue<E> {
	int limit;
	private LinkedList<E> queue = new LinkedList<>();
	private Lock monitor = new ReentrantLock();
	private Condition consumerWaitingCondition = monitor.newCondition();
	private Condition producerWaitingCondition = monitor.newCondition();

	public MyLinkedBlockingQueue(int limit) {
		this.limit = limit;
	}

	@Override
	public boolean add(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		try {
			monitor.lock();
			if (queue.size() == limit) {
				throw new IllegalStateException();
			}
			boolean res = queue.add(e);
			consumerWaitingCondition.signal();
			return res;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean offer(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		boolean res = true;
		try {
			monitor.lock();
			if (queue.size() == limit) {
				res = false;
			} else {
				queue.add(e);
				consumerWaitingCondition.signal();
			}
			return res;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public void put(E e) throws InterruptedException {
		if (e == null) {
			throw new NullPointerException();
		}
		try {
			monitor.lock();
			while (queue.size() == limit) {
				producerWaitingCondition.await();
			}
			queue.add(e);
			consumerWaitingCondition.signal();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		if (e == null) {
			throw new NullPointerException();
		}
		try {
			monitor.lock();
			while (queue.size() == limit) {
				if (!producerWaitingCondition.await(timeout, unit)) {
					return false;
				}
			}
			queue.add(e);
			consumerWaitingCondition.signal();
			return true;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		try {
			monitor.lock();
			while (queue.isEmpty()) {
				consumerWaitingCondition.await();
			}
			E res = queue.remove(0);
			producerWaitingCondition.signal();
			return res;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		try {
			monitor.lock();
			while (queue.isEmpty()) {
				if (!consumerWaitingCondition.await(timeout, unit)) {
					return null;
				}
			}
			E res = queue.remove();
			producerWaitingCondition.signal();
			return res;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E remove() {
		try {
			monitor.lock();
			E result = queue.remove();
			producerWaitingCondition.signal();
			return result;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E peek() {
		E result = null;
		try {
			monitor.lock();
			if (queue.size() != 0) {
				result = queue.get(0);
			}
			return result;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E element() {
		try {
			monitor.lock();
			return queue.element();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E poll() {
		E result = null;
		try {
			monitor.lock();
			result = queue.poll();
			if (result != null) {
				producerWaitingCondition.signal();
			}
			return result;
		} finally {
			monitor.unlock();
		}
	}

}