package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PrinterControllerAppl {

	public static void main(String[] args) throws InterruptedException {
		Printer printer1 = new Printer('#', 100);
		Printer printer2 = new Printer('*', 100);
		Instant start = Instant.now();
		printer1.start();
		printer1.join(); // main thread moves to waiting for finishing printer1
		printer2.start();
		printer2.join();// main thread moves to waiting for finishing printer2
						// in the case printer2 has already finished the main thread doesn't wait
		
		System.out.printf("running time is %dMs \n", ChronoUnit.MILLIS.between(start, Instant.now()));
	}

}
