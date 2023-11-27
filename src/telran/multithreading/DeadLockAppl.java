package telran.multithreading;

public class DeadLockAppl {

	public static void main(String[] args) {
		Worker worker1 = new Worker();
		Worker worker2 = new Worker();
		worker1.start();
		worker2.start();
	}
}
