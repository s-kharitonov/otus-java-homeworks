package ru.otus.jdbc.mapper.impl;

import ru.otus.annotations.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BasicEntityClassMetaData<T> implements EntityClassMetaData<T> {

	private final String name;
	private final Constructor<T> constructor;
	private final Field id;
	private final List<Field> allFields;
	private final List<Field> fieldsWithoutId;

	public BasicEntityClassMetaData(final Class<?> clazz) {
		this.name = clazz.getSimpleName();
		this.constructor = extractDefaultConstructor(clazz);
		this.allFields = extractFields(clazz);
		this.id = extractFieldId(allFields);
		this.fieldsWithoutId = extractFieldsWithoutId(allFields);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Constructor<T> getConstructor() {
		return constructor;
	}

	@Override
	public Field getIdField() {
		return id;
	}

	@Override
	public List<Field> getAllFields() {
		return allFields;
	}

	@Override
	public List<Field> getFieldsWithoutId() {
		return fieldsWithoutId;
	}

	@SuppressWarnings("unchecked")
	private Constructor<T> extractDefaultConstructor(final Class<?> clazz) {
		return (Constructor<T>) Arrays.stream(clazz.getConstructors())
				.filter((constructor) -> constructor.getParameterCount() == 0)
				.findFirst()
				.orElseThrow(() -> new RuntimeException("The default constructor is overridden!"));
	}

	private List<Field> extractFields(final Class<?> clazz) {
		return Arrays.stream(clazz.getDeclaredFields())
				.collect(Collectors.toList());
	}

	private List<Field> extractFieldsWithoutId(final List<Field> fields) {
		return fields.stream()
				.filter(field -> !field.isAnnotationPresent(Id.class))
				.collect(Collectors.toList());
	}

	private Field extractFieldId(final List<Field> fields) {
		return fields.stream()
				.filter(field -> field.isAnnotationPresent(Id.class))
				.findFirst()
				.orElseThrow();
	}
}
