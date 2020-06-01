package ru.otus.adapters.impl;

import ru.otus.adapters.TypeAdapter;

import javax.json.Json;
import javax.json.JsonValue;

public class NumberTypeAdapter implements TypeAdapter {
	private final Number number;

	public NumberTypeAdapter(final Number number) {
		this.number = number;
	}

	@Override
	public JsonValue getJsonValue() {
		return Json.createValue(String.valueOf(number));
	}
}
