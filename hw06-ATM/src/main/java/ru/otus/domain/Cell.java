package ru.otus.domain;

import java.util.List;

public class Cell {
	private final List<Banknote> banknotes;

	public Cell(final List<Banknote> banknotes) {
		this.banknotes = banknotes;
	}

	public List<Banknote> getBanknotes() {
		return banknotes;
	}
}
