package ru.otus.benchmarks;

import java.util.ArrayList;
import java.util.List;

public class OOMBenchmark {
	private final int loopCounter;
	private double growSize = 1d;

	public OOMBenchmark(final int loopCounter) {
		this.loopCounter = loopCounter;
	}

	public void run() {
		for (int i = 0; i < loopCounter; i++) {
			final List<String> numbers = new ArrayList<>();

			for (int j = 0; j < growSize; j++) {
				numbers.add(String.valueOf(j));
			}

			growSize *= 1.012;
		}
	}
}
