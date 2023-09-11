package telran.multithreading.games;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.IntStream;

import telran.view.*;

public class RaceAppl {

	private static final int MAX_THREADS = 10;
	private static final int MIN_THREADS = 3;
	private static final int MIN_DISTANCE = 100;
	private static final int MAX_DISTANCE = 3500;
	private static final int MIN_SLEEP = 2;
	private static final int MAX_SLEEP = 5;
	private static final int N_SPACE = 10;
	private static final int SPACE = 3;

	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		Item[] items = getItems();
		Menu menu = new Menu("Race Game", items);
		menu.perform(io);
	}

	private static Item[] getItems() {
		Item[] res = { Item.of("Start new game", RaceAppl::startGame), Item.ofExit() };
		return res;
	}

	static void startGame(InputOutput io) {
		int nThreads = io.readInt("Enter number of the runners", "Wrong number of the runners", MIN_THREADS,
				MAX_THREADS);
		int distance = io.readInt("Enter distance", "Wrong Distance", MIN_DISTANCE, MAX_DISTANCE);
		List<ResultTable> results = Collections.synchronizedList(new ArrayList<>());
		Race race = new Race(distance, MIN_SLEEP, MAX_SLEEP);
		Runner[] runners = new Runner[nThreads];
		Instant startRace = Instant.now();
		startRunners(runners, race, results);
		joinRunners(runners);
		displayResults(results, startRace, distance);
	}

	private static void displayResults(List<ResultTable> results, Instant startRace, int distance) {
		System.out.printf("Table of results at a distance %dm :\n " + "Place" + " ".repeat(SPACE) + "Racer number"
				+ " ".repeat(SPACE) + "Time\n", distance);

		int place = 1;
		for (ResultTable result : results) {
			long runningTime = ChronoUnit.MILLIS.between(startRace, result.finishTime);
			System.out.printf(" ".repeat(SPACE) + "%d" + " ".repeat(N_SPACE) + "%d" + " ".repeat(N_SPACE) + "%d\n",
					place++, result.runnerId, runningTime);
		}
	}

	private static void joinRunners(Runner[] runners) {
		IntStream.range(0, runners.length).forEach(i -> {
			try {
				runners[i].join();
			} catch (InterruptedException e) {
				throw new IllegalStateException();
			}
		});
	}

	private static void startRunners(Runner[] runners, Race race, List<ResultTable> results) {
		IntStream.range(0, runners.length).forEach(i -> {
			runners[i] = new Runner(race, i + 1, results);
			runners[i].start();
		});
	}

}