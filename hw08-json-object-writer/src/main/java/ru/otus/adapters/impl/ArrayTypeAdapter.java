package ru.otus.adapters.impl;

import ru.otus.adapters.TypeAdapter;
import ru.otus.factories.impl.BasicJsonValueFactory;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import java.util.Objects;

public class ArrayTypeAdapter implements TypeAdapter {

	private final Object[] array;

	public ArrayTypeAdapter(final Object[] array) {
		this.array = Objects.requireNonNull(array);
	}

	@Override
	public javax.json.JsonValue getJsonValue() {

		if (array.length == 0) {
			return JsonValue.EMPTY_JSON_ARRAY;
		}

		final JsonArrayBuilder builder = Json.createArrayBuilder();

		for (Object obj : array) {
			final JsonValue value = new BasicJsonValueFactory(obj).getJsonValue();

			builder.add(value);
		}

		return builder.build();
	}
}
