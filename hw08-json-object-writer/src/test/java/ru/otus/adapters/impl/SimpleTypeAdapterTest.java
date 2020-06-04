package ru.otus.adapters.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class SimpleTypeAdapterTest {

	private Gson gson;

	@BeforeEach
	void setUp() {
		gson = new GsonBuilder().create();
	}

	@ParameterizedTest
	@ValueSource(strings = {"Hello", "World", "!", ""})
	void getJsonValueString(final String simpleString) {
		final String simpleJsonValue = new SimpleTypeAdapter(simpleString)
				.getJsonValue()
				.toString();
		final String result = gson.fromJson(simpleJsonValue, String.class);

		assertTrue(simpleString.equalsIgnoreCase(result));
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void getJsonValueBoolean(final boolean value) {
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();
		final boolean result = gson.fromJson(simpleJsonValue, Boolean.class);

		assertEquals(result, value);
	}

	@ParameterizedTest
	@ValueSource(chars = {'h', 'e', 'l', 'o'})
	void getJsonValueCharacters(final char value) {
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();
		final char result = gson.fromJson(simpleJsonValue, Character.class);

		assertEquals(result, value);
	}

	@ParameterizedTest
	@ValueSource(bytes = {Byte.MAX_VALUE, Byte.MIN_VALUE})
	void getJsonValueBytes(final byte value) {
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();
		final byte result = gson.fromJson(simpleJsonValue, Byte.class);

		assertEquals(result, value);
	}

	@ParameterizedTest
	@ValueSource(shorts = {Short.MIN_VALUE, Short.MAX_VALUE})
	void getJsonValueShorts(final short value) {
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();
		final short result = gson.fromJson(simpleJsonValue, Short.class);

		assertEquals(result, value);
	}

	@ParameterizedTest
	@ValueSource(ints = {Integer.MAX_VALUE, Integer.MIN_VALUE})
	void getJsonValueInt(final int value) {
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();
		final int result = gson.fromJson(simpleJsonValue, Integer.class);

		assertEquals(result, value);
	}

	@ParameterizedTest
	@ValueSource(longs = {Long.MIN_VALUE, Long.MAX_VALUE})
	void getJsonValueLong(final long value) {
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();
		final long result = gson.fromJson(simpleJsonValue, Long.class);

		assertEquals(result, value);
	}

	@ParameterizedTest
	@ValueSource(floats = {Float.MIN_VALUE, Float.MAX_VALUE})
	void getJsonValueFloat(final float value) {
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();
		final float result = gson.fromJson(simpleJsonValue, Float.class);

		assertEquals(result, value);
	}

	@ParameterizedTest
	@ValueSource(doubles = {Double.MIN_VALUE, Double.MAX_VALUE})
	void getJsonValueDouble(final double value) {
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();
		final double result = gson.fromJson(simpleJsonValue, Double.class);

		assertEquals(result, value);
	}

	@Test
	void getJsonValueBigInt() {
		final String simpleJsonValue = new SimpleTypeAdapter(BigInteger.TEN)
				.getJsonValue()
				.toString();
		final BigInteger result = gson.fromJson(simpleJsonValue, BigInteger.class);

		assertEquals(result, BigInteger.TEN);
	}

	@Test
	void getJsonValueBigDecimal() {
		final String simpleJsonValue = new SimpleTypeAdapter(BigDecimal.TEN)
				.getJsonValue()
				.toString();
		final BigDecimal result = gson.fromJson(simpleJsonValue, BigDecimal.class);

		assertEquals(result, BigDecimal.TEN);
	}

	@ParameterizedTest
	@NullSource
	void getJsonValueWithEmptyParam(final Object value) {
		assertThrows(NullPointerException.class, () -> {
			new SimpleTypeAdapter(value);
		});
	}
}