package ru.otus.runners;

import ru.otus.exceptions.IllegalUsingAnnotationsException;

public interface Runner {
	void run(final String className) throws IllegalUsingAnnotationsException;
}
