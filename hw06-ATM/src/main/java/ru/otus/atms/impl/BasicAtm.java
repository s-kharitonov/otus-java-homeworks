package ru.otus.atms.impl;

import ru.otus.atms.Atm;
import ru.otus.domain.Banknote;
import ru.otus.domain.Cell;
import ru.otus.domain.MonetaryValue;
import ru.otus.exceptions.AtmModificationException;
import ru.otus.utils.CollectionUtils;

import java.util.*;
import java.util.function.Predicate;

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
	public List<Banknote> debitFromBalance(final int value) throws AtmModificationException {
		if (value <= 0) {
			throw new AtmModificationException("not valid value. Value must be more 0");
		}

		final int balance = calculateBalance();

		if (value > balance) {
			throw new AtmModificationException("not enough money");
		}

		final boolean isValidValue = value % MonetaryValue.FIFTY.getValue() == 0;

		if (!isValidValue) {
			throw new AtmModificationException("value must be a multiple of 50");
		}

		final List<Banknote> banknotes = new ArrayList<>();

		return banknotes;
	}

	@Override
	public int putOnBalance(final Banknote banknote) throws AtmModificationException {

		if (Objects.isNull(banknote)) {
			throw new AtmModificationException("banknote is null");
		}

		final int value = banknote.getValue();
		final boolean isValidValue = value > 0;

		if (!isValidValue) {
			throw new AtmModificationException("banknote has not valid value. Value must be more 0");
		}

		final Predicate<MonetaryValue> predicate = (monetaryValue) -> monetaryValue.getValue() == value;
		final MonetaryValue foundMonetaryValue = CollectionUtils.findValue(Arrays.asList(MonetaryValue.values()), predicate);

		if (Objects.isNull(foundMonetaryValue)) {
			throw new AtmModificationException("unknown banknote");
		}

		final Cell cell = this.balance.get(foundMonetaryValue);

		cell.getBanknotes().add(banknote);

		return calculateBalance();
	}
}
