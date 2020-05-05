package ru.otus;

import ru.otus.calculators.Calculator;
import ru.otus.calculators.impl.Addition;
import ru.otus.calculators.impl.Division;
import ru.otus.proxy.IOC;

public class Main {
	public static void main(String[] args) {
		final Calculator addition = IOC.createCalculator(new Addition());
		final Calculator division = IOC.createCalculator(new Division());

		addition.calculate(1, 1);
		division.calculate(1, 1);
	}
}
