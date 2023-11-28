package telran.multithreading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

public class ListOperationsControllerAppl {

	private static final int N_NUMBERS = 1000;
	private static final int UPDATE_PROB = 50;
	private static final int N_OPERATIONS = 100;
	private static final int N_THREADS = 100;

	public static void main(String[] args) throws InterruptedException {
		Integer[] array = new Integer[N_NUMBERS];
		Arrays.fill(array, 100);
		ArrayList<Integer> list = new ArrayList<>(Arrays.asList(array));

		ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
		Lock readLock = rwLock.readLock();
		Lock writeLock = rwLock.writeLock();
		AtomicInteger count = new AtomicInteger(0);
		Monitor monitor = new Monitor(readLock, writeLock, count);

		ExpData expData = new ExpData(UPDATE_PROB, N_OPERATIONS);
		ListOperations[] listThreads = new ListOperations[N_THREADS];

		for (int i = 0; i < listThreads.length; i++) {
			listThreads[i] = new ListOperations(monitor, list, expData);
			listThreads[i].start();
		}
		for (ListOperations oneThread : listThreads) {
			oneThread.join();
		}
		System.out.printf(" count of waiting iterations is " + count);
	}

}
