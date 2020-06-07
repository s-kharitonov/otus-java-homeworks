package ru.otus.adapters.impl;

import ru.otus.adapters.TypeAdapter;
import ru.otus.factories.impl.BasicJsonValueFactory;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import java.util.Collection;
import java.util.Objects;

public class CollectionTypeAdapter implements TypeAdapter {

	private final Collection<?> collection;

	public CollectionTypeAdapter(final Collection<?> collection) {
		this.collection = Objects.requireNonNull(collection);
	}

	@Override
	public JsonValue getJsonValue() {

		if (collection.isEmpty()) {
			return JsonValue.EMPTY_JSON_ARRAY;
		}

		final JsonArrayBuilder builder = Json.createArrayBuilder();

		for (Object obj : collection) {
			final JsonValue value = new BasicJsonValueFactory(obj).getJsonValue();

			builder.add(value);
		}

		return builder.build();
	}
}
