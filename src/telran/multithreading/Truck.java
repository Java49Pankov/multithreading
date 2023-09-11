package telran.multithreading;

public class Truck extends Thread {

	private int load;
	private static long elevator1;
	private static long elevator2;
	private static Object mutex = new Object();
	private int nLoads;

	public Truck(int load, int nLoads) {
		this.load = load;
		this.nLoads = nLoads;
	}

	public static long getElevator1() {
		return elevator1;
	}

	public static long getElevator2() {
		return elevator2;
	}

	@Override
	public void run() {
		for (int i = 0; i < nLoads; i++) {
			loadElevator1(load);
			loadElevator2(load);
		}
	}

	private static void loadElevator2(int load) {
		synchronized (mutex) {
			elevator2 += load;
		}
	}

	private static synchronized void loadElevator1(int load) {
		elevator1 += load;

	}
}
