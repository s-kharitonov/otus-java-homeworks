package ru.otus.factories;

import ru.otus.cells.Cell;
import ru.otus.domain.Banknote;

public interface CellFactory {
	Cell initCell(final Banknote banknote);
}
