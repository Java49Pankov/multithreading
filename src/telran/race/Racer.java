package telran.race;

import java.time.Instant;

public class Racer extends Thread {
	private int racers;
	private Race race;
	private Instant finishTime;

	public Racer(int racers, Race race) {
		this.racers = racers;
		this.race = race;
	}

	@Override
	public void run() {
		int distance = race.getDistance();
		int sleepRange = race.getSleepRange();
		for (int i = 0; i < distance; i++) {
			try {
				sleep(sleepRange);
			} catch (InterruptedException e) {
			}
		}
		finishTime = Instant.now();
	}

	public Instant getFinishTime() {
		return finishTime;
	}
	
	 public int getRacerNumber() {
	        return racers;
	    }

}
