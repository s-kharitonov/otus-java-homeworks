package ru.otus.validators;

public interface ObjectValidator<T>{
	boolean isValid(T obj);
}
