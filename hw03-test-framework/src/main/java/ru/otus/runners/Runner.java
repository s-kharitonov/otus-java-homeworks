package ru.otus.runners;

public interface Runner {
	<T> void run(final Class<T> clazz);
}
