package telran.multithreading;

import java.util.concurrent.ThreadFactory;

public class Printer extends Thread {

	private char symbol;
	private int nSymbols;

	public Printer(char symbol, int nSymbols) {
		this.symbol = symbol;
		this.nSymbols = nSymbols;
	}

	@Override
	public void run() {
		for (int i = 0; i < nSymbols; i++) {
			System.out.println(symbol);
			try {
				sleep(1);
			} catch (InterruptedException e) {

			}
		}
	}

}
