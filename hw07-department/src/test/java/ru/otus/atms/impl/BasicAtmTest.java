package ru.otus.atms.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atms.Atm;
import ru.otus.domain.Banknote;
import ru.otus.exceptions.AtmModificationException;
import ru.otus.factories.impl.BasicAtmFactory;

import java.util.List;

class BasicAtmTest {

	public static final int MIN_BALANCE_VALUE = 0;
	public static final int NEGATIVE_NUMBER = -1;
	public static final int NUMBER_IS_NOT_MULTIPLY_FIFTY = 1;

	private Atm atm;

	@BeforeEach
	void setUp() {
		atm = new BasicAtmFactory().initAtm();
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void calculateBalance() {
		final int balance = atm.calculateBalance();

		Assertions.assertTrue(balance >= MIN_BALANCE_VALUE);
	}

	@Test
	void debitFromBalanceParameterLessThanZero() {
		Assertions.assertThrows(AtmModificationException.class, () -> {
			atm.debitFromBalance(NEGATIVE_NUMBER);
		});
	}

	@Test
	void debitFromBalanceParameterMoreThanBalance() {
		final int balance = atm.calculateBalance();

		Assertions.assertThrows(AtmModificationException.class, () -> {
			atm.debitFromBalance(balance * 2);
		});
	}

	@Test
	void debitFromBalanceParameterMultiplyFifty() {
		Assertions.assertThrows(AtmModificationException.class, () -> {
			atm.debitFromBalance(NUMBER_IS_NOT_MULTIPLY_FIFTY);
		});
	}

	@Test
	void debitFromBalanceIsValidResult() throws AtmModificationException {
		final List<Banknote> banknotes = atm.debitFromBalance(Banknote.FIFTY.getValue());
		final int result = banknotes.stream().mapToInt(Banknote::getValue).sum();

		Assertions.assertEquals(Banknote.FIFTY.getValue(), result);
	}

	@Test
	void putOnBalanceWithEmptyParameter() {
		Assertions.assertThrows(AtmModificationException.class, () -> atm.putOnBalance(null));
	}

	@Test
	void putOnBalance() throws AtmModificationException {
		final int previousBalance = atm.calculateBalance();
		final int newBalance = previousBalance + Banknote.FIVE_THOUSAND.getValue();

		Assertions.assertEquals(newBalance, atm.putOnBalance(Banknote.FIVE_THOUSAND));
	}

	@Test
	void restart() throws AtmModificationException {
		final int startBalance = atm.calculateBalance();

		atm.putOnBalance(Banknote.FIVE_HUNDRED);
		atm.restart();

		Assertions.assertEquals(startBalance, atm.calculateBalance());
	}
}