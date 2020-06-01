package ru.otus.adapters.impl;

import ru.otus.adapters.TypeAdapter;
import ru.otus.factories.impl.BasicTypeAdapterFactory;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import java.util.Collection;

public class CollectionTypeAdapter implements TypeAdapter {

	private final Collection<?> collection;

	public CollectionTypeAdapter(final Collection<?> collection) {
		this.collection = collection;
	}

	@Override
	public JsonValue getJsonValue() {

		if (collection.isEmpty()) {
			return JsonValue.EMPTY_JSON_ARRAY;
		}

		final JsonArrayBuilder builder = Json.createArrayBuilder();

		for (Object obj : collection) {
			final JsonValue value = new BasicTypeAdapterFactory().getTypeAdapter(obj).getJsonValue();

			builder.add(value);
		}

		return builder.build();
	}
}
