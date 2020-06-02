package ru.otus.observers;

public interface EventManager {
	void subscribe(final String eventName, final EventListener listener);

	void unsubscribe(final String eventName, final EventListener listener);

	void notify(final String eventName);
}
