package ru.otus.adapters.impl;

import ru.otus.adapters.TypeAdapter;

import javax.json.Json;
import javax.json.JsonValue;

public class CharTypeAdapter implements TypeAdapter {

	private final Character character;

	public CharTypeAdapter(final Character character) {
		this.character = character;
	}

	@Override
	public JsonValue getJsonValue() {
		return Json.createValue(character);
	}
}
