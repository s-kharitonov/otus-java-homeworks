package ru.otus.adapters.impl;

import ru.otus.adapters.TypeAdapter;

import javax.json.Json;
import javax.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class SimpleTypeAdapter implements TypeAdapter {

	private final Object obj;
	private static final Map<Class<?>, Function<Object, JsonValue>> simpleTypes = Map.ofEntries(
			Map.entry(String.class, (o) -> Json.createValue((String) o)),
			Map.entry(Boolean.class, (o) -> (Boolean) o ? JsonValue.TRUE : JsonValue.FALSE),
			Map.entry(Character.class, (o) -> Json.createValue(((Character) o).toString())),
			Map.entry(Byte.class, (o) -> Json.createValue(((Byte) o).intValue())),
			Map.entry(Short.class, (o) -> Json.createValue(((Short) o).intValue())),
			Map.entry(Integer.class, (o) -> Json.createValue((Integer) o)),
			Map.entry(Long.class, (o) -> Json.createValue((Long) o)),
			Map.entry(Float.class, (o) -> Json.createValue(((Float) o).doubleValue())),
			Map.entry(Double.class, (o) -> Json.createValue((Double) o)),
			Map.entry(BigInteger.class, (o) -> Json.createValue((BigInteger) o)),
			Map.entry(BigDecimal.class, (o) -> Json.createValue((BigDecimal) o))
	);

	public SimpleTypeAdapter(final Object obj) {
		this.obj = Objects.requireNonNull(obj);
	}

	@Override
	public JsonValue getJsonValue() {
		return simpleTypes.getOrDefault(
				obj.getClass(),
				o -> {
					throw new IllegalArgumentException(String.format("type: %s is not are simple", o.getClass()));
				}
		).apply(obj);
	}
}
