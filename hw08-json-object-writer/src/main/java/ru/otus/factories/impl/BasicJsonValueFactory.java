package ru.otus.factories.impl;

import com.google.gson.internal.Primitives;
import ru.otus.adapters.impl.ArrayTypeAdapter;
import ru.otus.adapters.impl.CollectionTypeAdapter;
import ru.otus.adapters.impl.ObjectTypeAdapter;
import ru.otus.adapters.impl.SimpleTypeAdapter;
import ru.otus.factories.JsonValueFactory;

import javax.json.JsonValue;
import java.util.Collection;
import java.util.Objects;

public class BasicJsonValueFactory implements JsonValueFactory {

	private final Object obj;

	public BasicJsonValueFactory(final Object obj) {
		this.obj = obj;
	}

	@Override
	public JsonValue getJsonValue() {

		if (Objects.isNull(obj)) {
			return JsonValue.NULL;
		}

		final Class<?> clazz = obj.getClass();

		if (clazz.isArray()) {
			return new ArrayTypeAdapter(obj).getJsonValue();
		}

		if (Primitives.isWrapperType(clazz) || obj instanceof String || obj instanceof Number) {
			return new SimpleTypeAdapter(obj).getJsonValue();
		}

		if (obj instanceof Collection) {
			return new CollectionTypeAdapter((Collection<?>) obj).getJsonValue();
		}

		return new ObjectTypeAdapter(obj).getJsonValue();
	}
}
