package ru.otus.adapters.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayTypeAdapterTest {

	private Gson gson;

	@BeforeEach
	void setUp() {
		gson = new GsonBuilder().create();
	}

	@Test
	void getJsonValue() {
		final Object[] array = new Object[]{1d, "3", BigDecimal.TEN.doubleValue()};
		final String simpleJsonValue = new ArrayTypeAdapter(array)
				.getJsonValue()
				.toString();
		final Object[] result = gson.fromJson(simpleJsonValue, Object[].class);

		assertTrue(Arrays.deepEquals(result, array));
	}

	@ParameterizedTest
	@NullSource
	void getJsonValueWithEmptyParam(final Object value) {
		assertThrows(NullPointerException.class, () -> {
			new ArrayTypeAdapter(value);
		});
	}
}