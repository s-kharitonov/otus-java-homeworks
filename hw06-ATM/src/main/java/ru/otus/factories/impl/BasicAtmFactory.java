package ru.otus.factories.impl;

import com.google.common.collect.Lists;
import ru.otus.atms.Atm;
import ru.otus.atms.impl.BasicAtm;
import ru.otus.cells.Cell;
import ru.otus.cells.impl.BasicCell;
import ru.otus.domain.Banknote;
import ru.otus.factories.AtmFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BasicAtmFactory implements AtmFactory {
	@Override
	public Atm initAtm() {
		final Map<Banknote, Cell> balance = new TreeMap<>(Comparator.reverseOrder());

		for (Banknote banknote : Banknote.values()) {
			final List<Banknote> banknotes = Lists.newArrayList(banknote, banknote, banknote, banknote, banknote);

			final BasicCell cell = new BasicCell(banknotes);

			balance.put(banknote, cell);
		}

		return new BasicAtm(balance);
	}
}
