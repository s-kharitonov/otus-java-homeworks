package ru.otus;

import ru.otus.atms.Atm;
import ru.otus.domain.Banknote;
import ru.otus.exceptions.AtmModificationException;
import ru.otus.factories.AtmFactory;
import ru.otus.factories.impl.BasicAtmFactory;

public class Main {

	private static final AtmFactory atmFactory = new BasicAtmFactory();

	public static void main(String[] args) throws AtmModificationException {
		final Atm atm = atmFactory.initAtm();

		System.out.println("balance: " + atm.calculateBalance());
		System.out.println("balance: " + atm.putOnBalance(new Banknote(5000)));
		System.out.println("balance: " + atm.debitFromBalance(50));
		System.out.println("balance: " + atm.calculateBalance());
	}
}
