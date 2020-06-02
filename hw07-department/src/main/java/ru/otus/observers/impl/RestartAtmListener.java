package ru.otus.observers.impl;

import ru.otus.atms.Atm;
import ru.otus.observers.EventListener;

public class RestartAtmListener implements EventListener {

	private final Atm atm;

	public RestartAtmListener(final Atm atm) {
		this.atm = atm;
	}

	@Override
	public void execute() {
		atm.restart();
	}
}
