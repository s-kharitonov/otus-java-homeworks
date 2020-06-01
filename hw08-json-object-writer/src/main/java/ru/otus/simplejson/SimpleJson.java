package ru.otus.simplejson;

import ru.otus.adapters.TypeAdapter;
import ru.otus.factories.impl.BasicTypeAdapterFactory;

import javax.json.Json;
import javax.json.JsonValue;

public class SimpleJson {

	public String toJson(final Object obj) {
		final TypeAdapter adapter = new BasicTypeAdapterFactory().getTypeAdapter(obj);
		final JsonValue value = adapter.getJsonValue();

		if (JsonValue.ValueType.ARRAY.equals(value.getValueType())) {
			return Json.createArrayBuilder(value.asJsonArray())
					.build()
					.toString();
		}

		if (JsonValue.ValueType.OBJECT.equals(value.getValueType())) {
			return Json.createObjectBuilder(value.asJsonObject())
					.build()
					.toString();
		}

		return value.toString();
	}
}
