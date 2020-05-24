package ru.otus.atms;

import ru.otus.domain.Banknote;
import ru.otus.exceptions.AtmModificationException;

import java.util.List;

public interface Atm {
	int calculateBalance();

	List<Banknote> debitFromBalance(final int value) throws AtmModificationException;

	int putOnBalance(final Banknote banknote) throws AtmModificationException;
}
