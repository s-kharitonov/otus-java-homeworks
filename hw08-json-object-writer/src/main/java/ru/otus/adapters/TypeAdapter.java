package ru.otus.adapters;

import javax.json.JsonValue;

public interface TypeAdapter {
	JsonValue getJsonValue();
}
