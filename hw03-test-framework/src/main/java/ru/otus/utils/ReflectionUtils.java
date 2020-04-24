package ru.otus.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Predicate;

public class ReflectionUtils {

	public static Method[] filterMethodsByAnnotation(final Method[] methods, final Class<? extends Annotation> annotation) {
		final Predicate<Method> predicate = (method) -> method.isAnnotationPresent(annotation);

		return Arrays.stream(methods).filter(predicate).toArray(Method[]::new);
	}

	public static Method getMethodByAnnotation(final Method[] methods, final Class<? extends Annotation> annotation) {
		final Method[] filteredMethods = filterMethodsByAnnotation(methods, annotation);

		try {
			return filteredMethods[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
}
