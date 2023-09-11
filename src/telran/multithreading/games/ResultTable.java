package telran.multithreading.games;

import java.time.Instant;

public class ResultTable {
	int runnerId;
	Instant finishTime;

	public ResultTable(int runnerId, Instant finishTime) {
		this.runnerId = runnerId;
		this.finishTime = finishTime;
	}

}
