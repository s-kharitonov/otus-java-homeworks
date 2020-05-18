package ru.otus;

import ru.otus.atms.Atm;
import ru.otus.dao.AtmDAO;
import ru.otus.dao.impl.BasicAtmDAO;
import ru.otus.domain.Banknote;
import ru.otus.exceptions.AtmModificationException;

public class Main {

	private static final AtmDAO atmDAO = new BasicAtmDAO();

	public static void main(String[] args) throws AtmModificationException {
		final Atm atm = atmDAO.initAtm();

		System.out.println("balance: " + atm.calculateBalance());
		System.out.println("balance: " + atm.putOnBalance(new Banknote(5000)));
		System.out.println("balance: " + atm.debitFromBalance(50));
		System.out.println("balance: " + atm.calculateBalance());
	}
}
