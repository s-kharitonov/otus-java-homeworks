package ru.otus.departments.impl;

import ru.otus.atms.Atm;
import ru.otus.departments.Department;
import ru.otus.domain.Constants;
import ru.otus.observers.EventManager;
import ru.otus.observers.impl.RestartAtmListener;

import java.util.List;

public class BasicDepartment implements Department {

	private final EventManager eventManager;
	private final List<Atm> atms;

	public BasicDepartment(final List<Atm> atms, final EventManager eventManager) {
		this.atms = atms;
		this.eventManager = eventManager;

		subscribeAtms(atms);
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

	private void subscribeAtms(final List<Atm> atms) {
		for (Atm atm : atms) {
			eventManager.subscribe(Constants.RESTART_ATM_EVENT, new RestartAtmListener(atm));
		}
	}
}
