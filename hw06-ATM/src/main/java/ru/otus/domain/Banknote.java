package ru.otus.domain;

public class Banknote {
	private final int value;

	public Banknote(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
