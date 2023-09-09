package telran.multithreading;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Timer extends Thread {

	private static final DateTimeFormatter DATE_FORMATER_DEFAULT = DateTimeFormatter.ofPattern("h:mm:ss a");
	private static final long TIMEOUT_DEFAULT = 1000;
	private DateTimeFormatter dtf;
	private long timeout;

	public Timer(DateTimeFormatter dtf, long timeout) {
		this.dtf = dtf;
		this.timeout = timeout;
		setDaemon(true);
	}

	public Timer() {
		this(DATE_FORMATER_DEFAULT, TIMEOUT_DEFAULT);
	}

	@Override
	public void run() {
		boolean running = true;
		while (running) {
			System.out.println(LocalTime.now().format(dtf));
			try {
				sleep(timeout);
			} catch (InterruptedException e) {
				running = false;
			}
		}
	}
}
