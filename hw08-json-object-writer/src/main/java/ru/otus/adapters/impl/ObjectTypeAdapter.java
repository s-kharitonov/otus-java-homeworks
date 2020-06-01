package ru.otus.adapters.impl;

import ru.otus.adapters.TypeAdapter;
import ru.otus.factories.impl.BasicTypeAdapterFactory;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Field;

public class ObjectTypeAdapter implements TypeAdapter {

	private final Object obj;

	public ObjectTypeAdapter(final Object obj) {
		this.obj = obj;
	}

	@Override
	public JsonValue getJsonValue() {
		final Class<?> clazz = obj.getClass();
		final JsonObjectBuilder builder = Json.createObjectBuilder();

		for (Field field : clazz.getFields()) {
			try {
				final Object fieldValue = field.get(obj);
				final String fieldName = field.getName();
				final JsonValue jsonValue = new BasicTypeAdapterFactory()
						.getTypeAdapter(fieldValue)
						.getJsonValue();

				builder.add(fieldName, jsonValue);
			} catch (IllegalAccessException ignored) {
			}
		}

		return builder.build();
	}
}
