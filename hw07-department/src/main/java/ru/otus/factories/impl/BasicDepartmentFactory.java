package ru.otus.factories.impl;

import ru.otus.atms.Atm;
import ru.otus.departments.Department;
import ru.otus.departments.impl.BasicDepartment;
import ru.otus.domain.Constants;
import ru.otus.factories.AtmFactory;
import ru.otus.factories.DepartmentFactory;
import ru.otus.observers.EventManager;
import ru.otus.observers.impl.DepartmentEventManager;

import java.util.ArrayList;
import java.util.List;

public class BasicDepartmentFactory implements DepartmentFactory {

	private final AtmFactory atmFactory = new BasicAtmFactory();

	@Override
	public Department initDepartment() {
		final List<Atm> atms = new ArrayList<>();
		final EventManager eventManager = new DepartmentEventManager(Constants.RESTART_ATM_EVENT);

		for (int i = 0; i < 5; i++) {
			atms.add(atmFactory.initAtm());
		}

		return new BasicDepartment(atms, eventManager);
	}
}
