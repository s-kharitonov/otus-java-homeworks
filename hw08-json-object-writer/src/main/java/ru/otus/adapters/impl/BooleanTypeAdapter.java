package ru.otus.adapters.impl;

import ru.otus.adapters.TypeAdapter;

import javax.json.JsonValue;

public class BooleanTypeAdapter implements TypeAdapter {

	private final Boolean value;

	public BooleanTypeAdapter(final Boolean value) {
		this.value = value;
	}

	@Override
	public JsonValue getJsonValue() {
		return value ? JsonValue.TRUE : JsonValue.FALSE;
	}
}
