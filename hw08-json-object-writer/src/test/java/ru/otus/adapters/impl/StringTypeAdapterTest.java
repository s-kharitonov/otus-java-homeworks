package ru.otus.adapters.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.otus.adapters.TypeAdapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringTypeAdapterTest {

	@ParameterizedTest
	@ValueSource(strings = {"Hello", "World", "!", ""})
	void getJsonValueWithValidParam(final String someString) {
		final TypeAdapter adapter = new StringTypeAdapter(someString);
		final String json = adapter.getJsonValue().toString();

		assertEquals(json, someString);
	}

	@ParameterizedTest
	@NullSource
	void getJsonValueWithEmptyParam(final String someString) {
		final TypeAdapter adapter = new StringTypeAdapter(someString);
		final String json = adapter.getJsonValue().toString();

		assertEquals(json, someString);
	}
}