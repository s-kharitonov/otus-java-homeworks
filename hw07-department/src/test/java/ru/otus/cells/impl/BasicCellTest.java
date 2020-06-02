package ru.otus.cells.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.cells.Cell;
import ru.otus.domain.Banknote;
import ru.otus.factories.impl.BasicCellFactory;

import java.util.NoSuchElementException;

class BasicCellTest {

	public static final int MIN_BALANCE_VALUE = 0;
	public static final int MIN_SIZE_VALUE = 0;

	private Cell cell;

	@BeforeEach
	void setUp() {
		cell = new BasicCellFactory().initCell(Banknote.FIVE_THOUSAND);
	}

	@Test
	void getBanknoteNoSuchElements() {
		final int size = cell.size();

		for (int i = 0; i < size; i++) {
			cell.getBanknote();
		}

		Assertions.assertThrows(NoSuchElementException.class, () -> {
			cell.getBanknote();
		});
	}

	@Test
	void getBanknoteNotEmptyResult() {
		Assertions.assertNotEquals(null, cell.getBanknote());
	}

	@Test
	void addBanknoteEmptyParameter() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			cell.addBanknote(null);
		});
	}

	@Test
	void addBanknoteNotThrows() {
		Assertions.assertDoesNotThrow(() -> {
			cell.addBanknote(Banknote.FIVE_THOUSAND);
		});
	}

	@Test
	void calculateBalance() {
		final int balance = cell.calculateBalance();

		Assertions.assertTrue(balance >= MIN_BALANCE_VALUE);
	}

	@Test
	void size() {
		final int size = cell.size();

		Assertions.assertTrue(size >= MIN_SIZE_VALUE);
	}

	@Test
	void reset() {
		final int previousBalance = cell.calculateBalance();

		cell.getBanknote();
		cell.reset();

		Assertions.assertEquals(previousBalance, cell.calculateBalance());
	}
}