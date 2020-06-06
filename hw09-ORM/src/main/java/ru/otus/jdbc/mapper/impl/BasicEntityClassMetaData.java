package ru.otus.jdbc.mapper.impl;

import ru.otus.annotations.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BasicEntityClassMetaData<T> implements EntityClassMetaData<T> {

	private final Class<T> clazz;

	public BasicEntityClassMetaData(final Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public String getName() {
		return clazz.getSimpleName();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Constructor<T> getConstructor() {
		return (Constructor<T>) Arrays.stream(clazz.getConstructors())
				.findFirst()
				.orElse(null);
	}

	@Override
	public Field getIdField() {
		return Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(Id.class))
				.findFirst()
				.orElse(null);
	}

	@Override
	public List<Field> getAllFields() {
		return Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
	}

	@Override
	public List<Field> getFieldsWithoutId() {
		return Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> !field.isAnnotationPresent(Id.class))
				.collect(Collectors.toList());
	}
}
