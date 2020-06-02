package ru.otus.factories.impl;

import ru.otus.atms.Atm;
import ru.otus.atms.impl.BasicAtm;
import ru.otus.cells.Cell;
import ru.otus.domain.Banknote;
import ru.otus.factories.AtmFactory;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class BasicAtmFactory implements AtmFactory {
	@Override
	public Atm initAtm() {
		final Map<Banknote, Cell> balance = new TreeMap<>(Comparator.reverseOrder());

		for (Banknote banknote : Banknote.values()) {
			final Cell cell = new BasicCellFactory().initCell(banknote);

			balance.put(banknote, cell);
		}

		return new BasicAtm(balance);
	}
}
