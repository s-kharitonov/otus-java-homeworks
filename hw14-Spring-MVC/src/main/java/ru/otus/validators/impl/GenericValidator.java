package ru.otus.validators.impl;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import ru.otus.validators.ObjectValidator;

import javax.validation.Validation;
import javax.validation.Validator;

public class GenericValidator<T> implements ObjectValidator<T> {

	private final Validator validator = Validation.byDefaultProvider()
			.configure()
			.messageInterpolator(new ParameterMessageInterpolator())
			.buildValidatorFactory().getValidator();

	@Override
	public boolean isValid(final T obj) {
		return validator.validate(obj).size() == 0;
	}
}
