package ru.otus.atms;

import ru.otus.domain.Banknote;

import java.util.List;

public interface Atm {
	int calculateBalance();

	List<Banknote> debitFromBalance(final int value);

	int putOnBalance(final Banknote banknote);
}
