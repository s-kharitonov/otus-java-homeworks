package ru.otus.atms.impl;

import ru.otus.atms.Atm;
import ru.otus.domain.Banknote;
import ru.otus.domain.Cell;
import ru.otus.domain.MonetaryValue;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BasicAtm implements Atm {

	private final Map<MonetaryValue, Cell> balance;

	public BasicAtm(final Map<MonetaryValue, Cell> balance) {
		this.balance = balance;
	}

	@Override
	public int calculateBalance() {
		return balance.values().stream()
				.map(Cell::getBanknotes)
				.flatMap(Collection::stream)
				.mapToInt(Banknote::getValue)
				.sum();
	}

	@Override
	public List<Banknote> debitFromBalance(final int value) {
		return null;
	}

	@Override
	public int putOnBalance(final Banknote banknote) {
		return calculateBalance();
	}
}
