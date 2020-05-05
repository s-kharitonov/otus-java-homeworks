package ru.otus.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReflectionUtils {

	public static Set<Method> filterMethodsByAnnotation(final Method[] methods, final Class<? extends Annotation> annotation) {
		final Predicate<Method> predicate = (method) -> method.isAnnotationPresent(annotation);

		return Arrays.stream(methods).filter(predicate).collect(Collectors.toSet());
	}

}
