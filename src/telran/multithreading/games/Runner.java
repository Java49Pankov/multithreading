package telran.multithreading.games;

import java.time.Instant;
import java.util.List;

public class Runner extends Thread {
	private Race race;
	private int runnerId;
	private List<ResultTable> results;

	public Runner(Race race, int runnerId, List<ResultTable> results) {
		this.race = race;
		this.runnerId = runnerId;
		this.results = results;
	}

	@Override
	public void run() {
		int sleepRange = race.getMaxSleep() - race.getMinSleep() + 1;
		int minSleep = race.getMinSleep();
		int distance = race.getDistance();

		for (int i = 0; i < distance; i++) {
			try {
				sleep((long) (minSleep + Math.random() * sleepRange));
			} catch (InterruptedException e) {
				throw new IllegalStateException();
			}
		}
		results.add(new ResultTable(runnerId, Instant.now()));
	}
}
