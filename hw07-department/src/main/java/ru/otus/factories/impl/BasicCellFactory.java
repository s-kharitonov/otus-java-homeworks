package ru.otus.factories.impl;

import ru.otus.cells.Cell;
import ru.otus.cells.impl.BasicCell;
import ru.otus.domain.Banknote;
import ru.otus.factories.CellFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BasicCellFactory implements CellFactory {

	@Override
	public Cell initCell(final Banknote banknote) {
		final List<Banknote> banknotes = new ArrayList<>();

		final Random random = new Random();
		final int loopCounter = random.nextInt(10) + 1;

		for (int i = 0; i < loopCounter; i++) {
			banknotes.add(banknote);
		}

		return new BasicCell(banknotes, banknote.getValue());
	}
}
