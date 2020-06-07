package ru.otus.simplejson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import examples.SimpleTypesClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleJsonTest {

	private Gson gson;

	@BeforeEach
	void setUp() {
		gson = new GsonBuilder().create();
	}

	@Test
	void toJsonWithArrayParam() {
		final Object[] array = new Object[]{1d, "3", BigDecimal.TEN.doubleValue()};
		final String simpleJsonValue = new SimpleJson().toJson(array);
		final Object[] result = gson.fromJson(simpleJsonValue, Object[].class);

		assertTrue(Arrays.deepEquals(result, array));
	}

	@Test
	void toJsonWithObjParam() {
		final SimpleTypesClass objWithSimpleTypes = new SimpleTypesClass();
		final String simpleJsonValue = new SimpleJson().toJson(objWithSimpleTypes);
		final SimpleTypesClass result = gson.fromJson(simpleJsonValue, SimpleTypesClass.class);

		assertEquals(result, objWithSimpleTypes);
	}

	@ParameterizedTest
	@NullSource
	void getJsonValueWithEmptyParam(final Object value) {
		final String simpleJsonValue = new SimpleJson().toJson(value);
		final String gsonValue = gson.toJson(value);

		assertEquals(simpleJsonValue, gsonValue);
	}
}