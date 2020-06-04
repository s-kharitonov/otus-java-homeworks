package ru.otus.adapters.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import examples.CollectionTypesClass;
import examples.SimpleTypesClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ObjectTypeAdapterTest {

	private Gson gson;

	@BeforeEach
	void setUp() {
		gson = new GsonBuilder().serializeNulls().create();
	}

	@Test
	void getJsonValueObjectWithCollections() {
		final CollectionTypesClass objWithCollections = new CollectionTypesClass();
		final String simpleJsonValue = new ObjectTypeAdapter(objWithCollections)
				.getJsonValue()
				.toString();
		final CollectionTypesClass result = gson.fromJson(simpleJsonValue, CollectionTypesClass.class);

		assertEquals(result, objWithCollections);
	}

	@Test
	void getJsonValueObjectWithSimpleTypes() {
		final SimpleTypesClass objWithSimpleTypes = new SimpleTypesClass();
		final String simpleJsonValue = new ObjectTypeAdapter(objWithSimpleTypes)
				.getJsonValue()
				.toString();
		final SimpleTypesClass result = gson.fromJson(simpleJsonValue, SimpleTypesClass.class);

		assertEquals(result, objWithSimpleTypes);
	}

	@ParameterizedTest
	@NullSource
	void getJsonValueWithEmptyParam(final Object value) {
		assertThrows(NullPointerException.class, () -> {
			new ObjectTypeAdapter(value);
		});
	}
}