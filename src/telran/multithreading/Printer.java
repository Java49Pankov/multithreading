package telran.multithreading;

public class Printer extends Thread {
	int printer;
	Printer printerNext;
	private int printLength;
	static int nPortions;
	static int nNumbers;

	public Printer(int printer) {
		this.printer = printer;
		printLength = nNumbers / nPortions;
	}

	public static void setPortions(int nPortions) {
		Printer.nPortions = nPortions;
	}

	public static void nRuns(int nNumbers) {
		Printer.nNumbers = nNumbers;
	}

	public void setNextThread(Printer printerNext) {
		this.printerNext = printerNext;
	}

	@Override
	public void run() {
		int count = 0;
		while (count < nPortions) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				System.out.println((" " + printer).repeat(printLength));
				printerNext.interrupt();
				count++;
			}
		}
	}

}
