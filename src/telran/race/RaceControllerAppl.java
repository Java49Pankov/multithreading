package telran.race;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import telran.view.*;

public class RaceControllerAppl {

	private static final int MIN_THREAD = 3;
	private static final int MAX_THREAD = 10;
	private static final int MIN_DISTANCE = 100;
	private static final int MAX_DISTANCE = 3500;
	private static final int MIN_SLEEPS = 2;
	private static final int MAX_SLEEPS = 5;
	private static final int N_SYMBOLS = 50;

	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		Menu menu = new Menu("Race game", getItems());
		menu.perform(io);

	}

	private static Item[] getItems() {
		Item[] items = { Item.of("Start game", RaceControllerAppl::startNewGame), Item.ofExit() };
		return items;
	}

	static void startNewGame(InputOutput io) {
		int participants = io.readInt("How many racers are participating?", "Wrong: incorrect entry of participants",
				MIN_THREAD, MAX_THREAD);
		int distance = io.readInt("Race distance", "Wrong: incorrect entry of distation", MIN_DISTANCE, MAX_DISTANCE);
		Race race = new Race(distance, MIN_SLEEPS, MAX_SLEEPS, Instant.now());
		Racer[] racers = new Racer[participants];
		startGame(racers, race);
		threadsWait(racers);
		displayResult(racers, race);
	}

	private static void threadsWait(Racer[] racers) {
		for (Racer race : racers) {
			try {
				race.join();
			} catch (InterruptedException e) {
			}
		}
	}

	private static void startGame(Racer[] racer, Race race) {
		for (int i = 0; i < racer.length; i++) {
			racer[i] = new Racer(i, race);
			racer[i].start();
		}
	}

	private static void displayResult(Racer[] racers, Race race) {
		Racer firstRacer = findFirstRacer(racers, race);
		if (firstRacer != null) {
			System.out.println("-".repeat(N_SYMBOLS));
			printFirstRacer(firstRacer, race);
		}
		System.out.println("-".repeat(N_SYMBOLS));
		printFinishTimes(racers, race);
	}

	private static void printFinishTimes(Racer[] racers, Race race) {
		for (int i = 0; i < racers.length; i++) {
			Instant finishTime = racers[i].getFinishTime();
			long time = ChronoUnit.MILLIS.between(race.getStartTime(), finishTime);
			System.out.printf("Racer %d finished at %dMs, sleep range: %dMs\n", i + 1, time, race.getSleepRange());
		}
	}

	private static Racer findFirstRacer(Racer[] racers, Race race) {
		Racer firstRacer = null;
		Instant firstFinishTime = null;

		for (int i = 0; i < racers.length; i++) {
			Instant finishTime = racers[i].getFinishTime();

			if (firstRacer == null || finishTime.isBefore(firstFinishTime)) {
				firstRacer = racers[i];
				firstFinishTime = finishTime;
			}
		}

		return firstRacer;
	}

	private static void printFirstRacer(Racer racer, Race race) {
		Instant firstTime = racer.getFinishTime();
		long time = ChronoUnit.MILLIS.between(race.getStartTime(), firstTime);
		System.out.printf("Congratulations: Racer %d finished first at %dMs\n", racer.getRacerNumber() + 1, time);
	}

}
