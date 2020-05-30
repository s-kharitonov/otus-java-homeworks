package ru.otus.factories.impl;

import ru.otus.atms.Atm;
import ru.otus.departments.Department;
import ru.otus.departments.impl.BasicDepartment;
import ru.otus.factories.AtmFactory;
import ru.otus.factories.DepartmentFactory;

import java.util.ArrayList;
import java.util.List;

public class BasicDepartmentFactory implements DepartmentFactory {

	private final AtmFactory atmFactory = new BasicAtmFactory();

	@Override
	public Department initDepartment() {
		final List<Atm> atms = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			atms.add(atmFactory.initAtm());
		}

		return new BasicDepartment(atms);
	}
}
