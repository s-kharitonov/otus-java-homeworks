package ru.otus.factories.impl;

import ru.otus.atms.Atm;
import ru.otus.atms.impl.BasicAtm;
import ru.otus.cells.Cell;
import ru.otus.cells.impl.BasicCell;
import ru.otus.domain.Banknote;
import ru.otus.factories.AtmFactory;

import java.util.*;

public class BasicAtmFactory implements AtmFactory {
	@Override
	public Atm initAtm() {
		final TreeMap<Banknote, Cell> balance = new TreeMap<>(Comparator.reverseOrder());

		for (Banknote banknote : Banknote.values()) {
			final List<Banknote> banknotes = createBanknotes(banknote);

			final BasicCell cell = new BasicCell(banknotes, banknote.getValue());

			balance.put(banknote, cell);
		}

		return new BasicAtm(balance);
	}

	private List<Banknote> createBanknotes(final Banknote banknote) {
		final List<Banknote> banknotes = new ArrayList<>();
		final Random random = new Random();
		final int loopCounter = random.nextInt(10) + 1;

		for (int i = 0; i < loopCounter; i++) {
			banknotes.add(banknote);
		}

		return banknotes;
	}
}
