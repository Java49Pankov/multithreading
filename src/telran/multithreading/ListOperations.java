package telran.multithreading;

import java.util.*;
import java.util.concurrent.locks.Lock;

public class ListOperations extends Thread {
	Random gen = new Random();
	Monitor monitor;
	ArrayList<Integer> list;
	ExpData expData;

	public ListOperations(Monitor monitor, ArrayList<Integer> list, ExpData expData) {
		this.monitor = monitor;
		this.list = list;
		this.expData = expData;
	}

	@Override
	public void run() {
		int nRuns = expData.nOperations();
		for (int i = 0; i < nRuns; i++) {
			if (gen.nextInt(1, 100) <= expData.updateProb()) {
				updateList();
			} else {
				readList();
			}
		}
	}

	private void readList() {
		int nUpdates = 10000;
		try {
			tryLock(monitor.read());
			for (int i = 0; i < nUpdates; i++) {
				list.get(list.size() - 1);
			}
		} finally {
			monitor.read().unlock();
		}
	}

	private void updateList() {
		int nUpdates = 10;
		try {
			tryLock(monitor.write());
			for (int i = 0; i < nUpdates; i++) {
				list.remove(0);
			}
			for (int i = 0; i < nUpdates; i++) {
				list.add(100);
			}
		} finally {
			monitor.write().unlock();
		}
	}

	private void tryLock(Lock lock) {
		while (!lock.tryLock()) {
			monitor.count().incrementAndGet();
		}
	}
}
