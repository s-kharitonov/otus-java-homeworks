package ru.otus.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Predicate;

public class ReflectionUtils {

	public static Method[] filterMethodsByAnnotation(final Method[] methods, final Class<? extends Annotation> annotation) {
		final Predicate<Method> predicate = (method) -> method.isAnnotationPresent(annotation);

		return (Method[]) Arrays.stream(methods).filter(predicate).toArray();
	}
}
