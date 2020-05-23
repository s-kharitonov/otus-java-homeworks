package ru.otus.factories.impl;

import com.google.common.collect.Lists;
import ru.otus.atms.Atm;
import ru.otus.atms.impl.BasicAtm;
import ru.otus.domain.Banknote;
import ru.otus.domain.Cell;
import ru.otus.domain.MonetaryValue;
import ru.otus.factories.AtmFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BasicAtmFactory implements AtmFactory {
	@Override
	public Atm initAtm() {
		final Map<MonetaryValue, Cell> balance = new TreeMap<>(Comparator.reverseOrder());

		for (MonetaryValue monetaryValue : MonetaryValue.values()) {
			final int value = monetaryValue.getValue();
			final List<Banknote> banknotes = Lists.newArrayList(new Banknote(value), new Banknote(value), new Banknote(value), new Banknote(value), new Banknote(value));

			final Cell cell = new Cell(banknotes);

			balance.put(monetaryValue, cell);
		}

		return new BasicAtm(balance);
	}
}
