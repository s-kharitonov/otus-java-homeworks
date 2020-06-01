package ru.otus.adapters.impl;

import ru.otus.adapters.TypeAdapter;
import ru.otus.factories.impl.BasicTypeAdapterFactory;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;

public class ArrayTypeAdapter implements TypeAdapter {

	private final Object[] array;

	public ArrayTypeAdapter(final Object[] array) {
		this.array = array;
	}

	@Override
	public JsonValue getJsonValue() {

		if (array.length == 0) {
			return JsonValue.EMPTY_JSON_ARRAY;
		}

		final JsonArrayBuilder builder = Json.createArrayBuilder();

		for (Object obj : array) {
			final JsonValue value = new BasicTypeAdapterFactory().getTypeAdapter(obj).getJsonValue();

			builder.add(value);
		}

		return builder.build();
	}
}
