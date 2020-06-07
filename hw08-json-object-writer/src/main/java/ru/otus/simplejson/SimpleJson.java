package ru.otus.simplejson;

import ru.otus.factories.impl.BasicJsonValueFactory;

import javax.json.Json;
import javax.json.JsonValue;

public class SimpleJson {

	public String toJson(final Object obj) {
		final JsonValue jsonValue = new BasicJsonValueFactory(obj).getJsonValue();

		if (JsonValue.ValueType.ARRAY.equals(jsonValue.getValueType())) {
			return Json.createArrayBuilder(jsonValue.asJsonArray())
					.build()
					.toString();
		}

		if (JsonValue.ValueType.OBJECT.equals(jsonValue.getValueType())) {
			return Json.createObjectBuilder(jsonValue.asJsonObject())
					.build()
					.toString();
		}

		return jsonValue.toString();
	}
}
