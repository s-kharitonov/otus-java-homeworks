package ru.otus.departments.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.departments.Department;
import ru.otus.factories.impl.BasicDepartmentFactory;

class BasicDepartmentTest {

	public static final int MIN_BALANCE_VALUE = 0;

	private Department department;

	@BeforeEach
	void setUp() {
		department = new BasicDepartmentFactory().initDepartment();
	}

	@Test
	void calculateBalance() {
		final int balance = department.calculateBalance();

		Assertions.assertTrue(balance >= MIN_BALANCE_VALUE);
	}

	@Test
	void restartAtms() {
		final int previousBalance = department.calculateBalance();

		department.restartAtms();
		Assertions.assertEquals(previousBalance, department.calculateBalance());
	}
}