package ru.otus.adapters.impl;

import ru.otus.adapters.TypeAdapter;

import javax.json.JsonValue;

public class NullTypeAdapter implements TypeAdapter {

	@Override
	public JsonValue getJsonValue() {
		return JsonValue.NULL;
	}
}
