package ru.otus.utils;

import java.util.Collection;
import java.util.function.Predicate;

public class CollectionUtils {

	public static <T> T findValue(Collection<T> collection, Predicate<T> predicate) {
		return collection.stream().filter(predicate).findFirst().orElse(null);
	}
}
