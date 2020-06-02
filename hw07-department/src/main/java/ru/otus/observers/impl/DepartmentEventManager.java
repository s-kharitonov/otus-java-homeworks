package ru.otus.observers.impl;

import ru.otus.observers.EventListener;
import ru.otus.observers.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentEventManager implements EventManager {

	private final Map<String, List<EventListener>> listeners = new HashMap<>();

	public DepartmentEventManager(String... eventNames) {
		for (String name : eventNames) {
			listeners.put(name, new ArrayList<>());
		}
	}

	@Override
	public void subscribe(final String eventName, final EventListener listener) {
		final List<EventListener> listeners = this.listeners.get(eventName);

		listeners.add(listener);
	}

	@Override
	public void unsubscribe(final String eventName, final EventListener listener) {
		final List<EventListener> listeners = this.listeners.get(eventName);

		listeners.remove(listener);
	}

	@Override
	public void notify(final String eventName) {
		final List<EventListener> listeners = this.listeners.get(eventName);

		listeners.forEach(EventListener::execute);
	}
}
