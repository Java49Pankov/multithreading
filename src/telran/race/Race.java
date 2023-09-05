package telran.race;

import java.time.Instant;
import java.util.Random;

public class Race {

	private int distance;
	private int minSleep;
	private int maxSleep;
	private Instant startTime;

	public Race(int distance, int minSleep, int maxSleep, Instant startTime) {
		this.distance = distance;
		this.minSleep = minSleep;
		this.maxSleep = maxSleep;
		this.startTime = startTime;
	}

	public int getDistance() {
		return distance;
	}

	public Instant getStartTime() {
		return startTime;
	}

	public int getSleepRange() {
		return new Random().nextInt(maxSleep - minSleep + 1) + minSleep;
	}
}
