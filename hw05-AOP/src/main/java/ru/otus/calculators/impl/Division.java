package ru.otus.calculators.impl;

import ru.otus.calculators.Calculator;

public class Division implements Calculator {

	@Override
	public double calculate(final double firstOperand, final double secondOperand) {
		return firstOperand / secondOperand;
	}
}
