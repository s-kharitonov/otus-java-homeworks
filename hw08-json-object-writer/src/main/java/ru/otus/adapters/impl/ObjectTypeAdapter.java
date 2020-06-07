package ru.otus.adapters.impl;

import ru.otus.adapters.TypeAdapter;
import ru.otus.factories.impl.BasicJsonValueFactory;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class ObjectTypeAdapter implements TypeAdapter {

	private final Object obj;

	public ObjectTypeAdapter(final Object obj) {
		this.obj = Objects.requireNonNull(obj);
	}

	@Override
	public JsonValue getJsonValue() {
		final Class<?> clazz = obj.getClass();
		final JsonObjectBuilder builder = Json.createObjectBuilder();
		final Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			try {

				final int modifiers = field.getModifiers();

				field.setAccessible(true);

				if (Modifier.isTransient(modifiers) || Modifier.isStatic(modifiers) || Modifier.isAbstract(modifiers)) {
					continue;
				}

				final Object fieldValue = field.get(obj);
				final String fieldName = field.getName();
				final JsonValue jsonValue = new BasicJsonValueFactory(fieldValue).getJsonValue();

				builder.add(fieldName, jsonValue);
			} catch (IllegalAccessException ignored) {
			}
		}

		return builder.build();
	}
}
