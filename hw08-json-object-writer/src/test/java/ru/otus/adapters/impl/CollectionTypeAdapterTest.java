package ru.otus.adapters.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CollectionTypeAdapterTest {

	private Gson gson;

	@BeforeEach
	void setUp() {
		gson = new GsonBuilder().create();
	}

	@Test
	void getJsonValue() {
		final Collection<?> collection = Lists.newArrayList(1d, "1", BigDecimal.TEN.doubleValue());
		final String simpleJsonValue = new CollectionTypeAdapter(collection)
				.getJsonValue()
				.toString();
		final Collection<?> result = gson.fromJson(simpleJsonValue, Collection.class);

		assertEquals(result, collection);
	}

	@ParameterizedTest
	@NullSource
	void getJsonValueWithEmptyParam(final Collection<?> value) {
		assertThrows(NullPointerException.class, () -> {
			new CollectionTypeAdapter(value);
		});
	}
}