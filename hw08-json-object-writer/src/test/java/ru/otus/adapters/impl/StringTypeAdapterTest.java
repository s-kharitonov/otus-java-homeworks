package ru.otus.adapters.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.otus.adapters.TypeAdapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringTypeAdapterTest {

	private Gson gson;

	@BeforeEach
	void setUp() {
		gson = new GsonBuilder().create();
	}

	@ParameterizedTest
	@ValueSource(strings = {"Hello", "World", "!", ""})
	void getJsonValueWithValidParam(final String someString) {
		final TypeAdapter adapter = new StringTypeAdapter(someString);
		final String gsonValue = gson.toJson(someString);
		final String simpleJsonValue = adapter.getJsonValue().toString();

		assertEquals(gsonValue, simpleJsonValue);
	}

	@ParameterizedTest
	@NullSource
	void getJsonValueWithEmptyParam(final String someString) {
		assertThrows(NullPointerException.class, () -> {
			new StringTypeAdapter(someString);
		});
	}
}