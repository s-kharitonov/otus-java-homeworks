package ru.otus.calculators.impl;

import ru.otus.annotations.Log;
import ru.otus.calculators.Calculator;

public class Addition implements Calculator {

	@Override
	@Log
	public double calculate(final double firstOperand, final double secondOperand) {
		return firstOperand + secondOperand;
	}
}
