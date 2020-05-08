package ru.otus;

import ru.otus.calculators.Calculator;
import ru.otus.calculators.impl.Addition;
import ru.otus.proxy.IOC;

public class Main {
	public static void main(String[] args) {
		final Calculator addition = IOC.getInstance(new Addition());

		addition.calculate(1, 1);
	}
}
