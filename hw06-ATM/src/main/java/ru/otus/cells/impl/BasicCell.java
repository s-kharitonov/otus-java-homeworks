package ru.otus.cells.impl;

import ru.otus.cells.Cell;
import ru.otus.domain.Banknote;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class BasicCell implements Cell {
	private final List<Banknote> banknotes;
	private final int nominal;

	public BasicCell(final List<Banknote> banknotes, final int nominal) {
		this.banknotes = banknotes;
		this.nominal = nominal;
	}

	@Override
	public Banknote getBanknote() throws NoSuchElementException {
		if (banknotes.isEmpty()) {
			throw new NoSuchElementException();
		}

		final int indexLastElement = banknotes.size() - 1;
		final Banknote banknote = banknotes.get(indexLastElement);

		banknotes.remove(indexLastElement);

		return banknote;
	}

	@Override
	public void addBanknote(final Banknote banknote) throws IllegalArgumentException {

		if (Objects.isNull(banknote)) {
			throw new IllegalArgumentException("banknote is null");
		}

		if (banknote.getValue() != nominal) {
			throw new IllegalArgumentException("illegal nominal");
		}

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
