package ru.otus.adapters.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleTypeAdapterTest {

	private Gson gson;

	@BeforeEach
	void setUp() {
		gson = new GsonBuilder().create();
	}

	@ParameterizedTest
	@ValueSource(strings = {"Hello", "World", "!", ""})
	void getJsonValueString(final String simpleString) {
		final String gsonValue = gson.toJson(simpleString);
		final String simpleJsonValue = new SimpleTypeAdapter(simpleString)
				.getJsonValue()
				.toString();

		assertEquals(gsonValue, simpleJsonValue);
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void getJsonValueBoolean(final boolean value) {
		final String gsonValue = gson.toJson(value);
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();

		assertEquals(gsonValue, simpleJsonValue);
	}

	@ParameterizedTest
	@ValueSource(chars = {'h', 'e', 'l', 'o'})
	void getJsonValueCharacters(final char value) {
		final String gsonValue = gson.toJson(value);
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();

		assertEquals(gsonValue, simpleJsonValue);
	}

	@ParameterizedTest
	@ValueSource(bytes = {Byte.MAX_VALUE, Byte.MIN_VALUE})
	void getJsonValueBytes(final byte value) {
		final String gsonValue = gson.toJson(value);
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();

		assertEquals(gsonValue, simpleJsonValue);
	}

	@ParameterizedTest
	@ValueSource(shorts = {Short.MIN_VALUE, Short.MAX_VALUE})
	void getJsonValueShorts(final short value) {
		final String gsonValue = gson.toJson(value);
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();

		assertEquals(gsonValue, simpleJsonValue);
	}

	@ParameterizedTest
	@ValueSource(ints = {Integer.MAX_VALUE, Integer.MIN_VALUE})
	void getJsonValueInt(final int value) {
		final String gsonValue = gson.toJson(value);
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();

		assertEquals(gsonValue, simpleJsonValue);
	}

	@ParameterizedTest
	@ValueSource(longs = {Long.MIN_VALUE, Long.MAX_VALUE})
	void getJsonValueLong(final long value) {
		final String gsonValue = gson.toJson(value);
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();

		assertEquals(gsonValue, simpleJsonValue);
	}

	@ParameterizedTest
	@ValueSource(floats = {Float.MIN_VALUE, Float.MAX_VALUE})
	void getJsonValueFloat(final float value) {
		final String gsonValue = gson.toJson(value);
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();

		assertEquals(gsonValue, simpleJsonValue);
	}

	@ParameterizedTest
	@ValueSource(doubles = {Double.MIN_VALUE, Double.MAX_VALUE})
	void getJsonValueDouble(final double value) {
		final String gsonValue = gson.toJson(value);
		final String simpleJsonValue = new SimpleTypeAdapter(value)
				.getJsonValue()
				.toString();

		assertEquals(gsonValue, simpleJsonValue);
	}

	@Test
	void getJsonValueBigInt() {
		final String gsonValue = gson.toJson(BigInteger.TEN);
		final String simpleJsonValue = new SimpleTypeAdapter(BigInteger.TEN)
				.getJsonValue()
				.toString();

		assertEquals(gsonValue, simpleJsonValue);
	}

	@Test
	void getJsonValueBigDecimal() {
		final String gsonValue = gson.toJson(BigDecimal.TEN);
		final String simpleJsonValue = new SimpleTypeAdapter(BigDecimal.TEN)
				.getJsonValue()
				.toString();

		assertEquals(gsonValue, simpleJsonValue);
	}
}