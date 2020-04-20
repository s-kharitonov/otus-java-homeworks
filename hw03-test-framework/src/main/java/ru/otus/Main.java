package ru.otus;

import ru.otus.exceptions.IllegalUsingAnnotationsException;
import ru.otus.runners.impl.BasicRunner;

public class Main {

	public static void main(String[] args) throws IllegalUsingAnnotationsException {
		new BasicRunner().run("ru.otus.TestExample");
	}
}
