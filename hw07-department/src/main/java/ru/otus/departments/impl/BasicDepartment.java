package ru.otus.departments.impl;

import ru.otus.atms.Atm;
import ru.otus.departments.Department;

import java.util.List;

public class BasicDepartment implements Department {

	private final List<Atm> atms;

	public BasicDepartment(final List<Atm> atms) {
		this.atms = atms;
	}

	@Override
	public int calculateBalance() {
		return atms.stream().mapToInt(Atm::calculateBalance).sum();
	}

	@Override
	public void restartAtms() {
		atms.forEach(Atm::restart);
	}
}
