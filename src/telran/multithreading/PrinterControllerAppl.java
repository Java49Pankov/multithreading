package telran.multithreading;

import java.util.*;

public class PrinterControllerAppl {

	public static void main(String[] args) {
		// Interview question: how to join itself in the main thread
		Thread.currentThread().interrupt();
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			System.out.println("interrupted");
		}
		Printer printer = new Printer(".*#$%&");
		try (Scanner scanner = new Scanner(System.in)) {
			printer.start();
			while (true) {
				String line = scanner.nextLine();
				if (line.equals("q")) {
					break;
				}
				printer.interrupt();
			}
		}
		printer.stopPrint();
	}
}
