package ru.otus.cells.impl;

import ru.otus.cells.Cell;
import ru.otus.domain.Banknote;

import java.util.List;
import java.util.NoSuchElementException;

public class BasicCell implements Cell {
	private final List<Banknote> banknotes;

	public BasicCell(final List<Banknote> banknotes) {
		this.banknotes = banknotes;
	}

	@Override
	public Banknote getBanknote() throws NoSuchElementException {
		if (banknotes.isEmpty()) {
			throw new NoSuchElementException();
		}

		final Banknote banknote = banknotes.get(0);

		banknotes.remove(0);

		return banknote;
	}

	@Override
	public void addBanknote(final Banknote banknote) {
		banknotes.add(banknote);
	}

	@Override
	public int calculateBalance() {
		return banknotes.stream()
				.mapToInt(Banknote::getValue)
				.sum();
	}

	@Override
	public int size() {
		return banknotes.size();
	}

	@Override
	public String toString() {
		return "BasicCell{" +
				"banknotes=" + banknotes +
				'}';
	}
}
