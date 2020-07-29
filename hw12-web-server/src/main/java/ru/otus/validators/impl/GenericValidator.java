package ru.otus.validators.impl;

import ru.otus.validators.ObjectValidator;

import javax.validation.Validator;

public class GenericValidator<T> implements ObjectValidator<T> {

	private final Validator validator;

	public GenericValidator(final Validator validator) {
		this.validator = validator;
	}

	@Override
	public boolean isValid(final T obj) {
		return validator.validate(obj).size() == 0;
	}
}
