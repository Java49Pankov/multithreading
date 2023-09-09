package telran.multithreading;

public class PrinterControllerAppl {

	private static final int N_THREADS = 4;
	private static final int N_PORTIONS = 10;
	private static final int N_NUMBERS = 100;

	public static void main(String[] args) {
		Printer.setPortions(N_PORTIONS);
		Printer.nRuns(N_NUMBERS);
		Printer[] printers = new Printer[N_THREADS];
		runThreads(printers);
		printers[0].interrupt();

	}

	private static void runThreads(Printer[] printers) {
		printers[0] = new Printer(1);
		for (int i = 1; i < printers.length; i++) {
			printers[i] = new Printer(i + 1);
			printers[i - 1].setNextThread(printers[i]);
			printers[i - 1].start();
		}
		printers[printers.length - 1].setNextThread(printers[0]);
		printers[printers.length - 1].start();
	}
}
