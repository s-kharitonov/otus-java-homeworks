package ru.otus.departments.impl;

import ru.otus.atms.Atm;
import ru.otus.departments.Department;
import ru.otus.domain.Constants;
import ru.otus.observers.EventManager;

import java.util.List;

public class BasicDepartment implements Department {

	private final EventManager eventManager;
	private final List<Atm> atms;

	public BasicDepartment(final List<Atm> atms, final EventManager eventManager) {
		this.atms = atms;
		this.eventManager = eventManager;
	}

	@Override
	public int calculateBalance() {
		return atms.stream()
				.mapToInt(Atm::calculateBalance)
				.sum();
	}

	@Override
	public void restartAtms() {
		eventManager.notify(Constants.RESTART_ATM_EVENT);
	}
}
