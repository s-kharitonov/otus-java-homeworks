package ru.otus;

import ru.otus.calculators.Calculator;
import ru.otus.calculators.impl.Addition;
import ru.otus.calculators.impl.Division;
import ru.otus.proxy.IOC;

public class Main {
	public static void main(String[] args) {
		final Calculator addition = IOC.getInstance(new Addition(), Calculator.class);
		final Calculator division = IOC.getInstance(new Division(), Calculator.class);

		addition.calculate(1, 1);
		division.calculate(1, 1);
	}
}
