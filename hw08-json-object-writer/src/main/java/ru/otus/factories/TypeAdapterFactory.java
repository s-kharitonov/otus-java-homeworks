package ru.otus.factories;

import ru.otus.adapters.TypeAdapter;

public interface TypeAdapterFactory {
	TypeAdapter getTypeAdapter(final Object obj);
}
