package ru.otus;

import ru.otus.atms.Atm;
import ru.otus.dao.AtmDAO;
import ru.otus.dao.impl.BasicAtmDAO;

public class Main {

	private static final AtmDAO atmDAO = new BasicAtmDAO();

	public static void main(String[] args) {
		final Atm atm = atmDAO.initAtm();

		System.out.println("balance: " + atm.calculateBalance());
	}
}
