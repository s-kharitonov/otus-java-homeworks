package ru.otus.factories.impl;

import ru.otus.adapters.TypeAdapter;
import ru.otus.adapters.impl.*;
import ru.otus.factories.TypeAdapterFactory;

import java.util.Collection;
import java.util.Objects;

public class BasicTypeAdapterFactory implements TypeAdapterFactory {

	@Override
	public TypeAdapter getTypeAdapter(final Object obj) {

		if (Objects.isNull(obj)) {
			return new NullTypeAdapter();
		}

		if (obj instanceof Number) {
			return new NumberTypeAdapter((Number) obj);
		}

		if (obj instanceof String) {
			return new StringTypeAdapter((String) obj);
		}

		if (obj instanceof Character) {
			return new CharTypeAdapter((Character) obj);
		}

		if (obj instanceof Boolean) {
			return new BooleanTypeAdapter((Boolean) obj);
		}

		if (obj.getClass().isArray()) {
			return new ArrayTypeAdapter((Object[]) obj);
		}

		if (obj instanceof Collection) {
			return new CollectionTypeAdapter((Collection<?>) obj);
		}

		return new ObjectTypeAdapter(obj);
	}
}
