package ru.otus.domain;

public enum MonetaryValue {
	FIFTY(50),
	HUNDRED(100),
	FIVE_HUNDRED(500),
	THOUSAND(1000),
	FIVE_THOUSAND(5000);

	private final int value;

	MonetaryValue(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
