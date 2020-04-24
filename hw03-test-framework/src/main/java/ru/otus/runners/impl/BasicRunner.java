package ru.otus.runners.impl;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.domain.Constants;
import ru.otus.runners.Runner;
import ru.otus.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Objects;

public class BasicRunner implements Runner {

	private int executed;
	private int passed;
	private int failed;

	@Override
	public void run(final String className) {
		final Class<?> clazz = getClassForName(className);
		final Method[] methods = Objects.requireNonNull(clazz).getMethods();
		final Method[] testMethods = ReflectionUtils.filterMethodsByAnnotation(methods, Test.class);
		final Method beforeMethod = ReflectionUtils.getMethodByAnnotation(methods, Before.class);
		final Method afterMethod = ReflectionUtils.getMethodByAnnotation(methods, After.class);

		for (Method testMethod : testMethods) {
			executeTest(clazz, beforeMethod, afterMethod, testMethod);
		}

		final String result = String.format(Constants.TEST_RESULT, executed, passed, failed);

		System.out.println(Constants.TESTS_FINISHED);
		System.out.println(result);
	}

	private <T> void executeTest(final Class<T> clazz, final Method beforeMethod, final Method afterMethod, final Method testMethod) {
		try {
			final T testObject = clazz.getDeclaredConstructor().newInstance();

			if (!Objects.isNull(beforeMethod)) {
				beforeMethod.invoke(testObject);
			}

			testMethod.invoke(testObject);

			if (!Objects.isNull(beforeMethod)) {
				afterMethod.invoke(testObject);
			}

			passed++;
		} catch (Exception e) {
			e.printStackTrace();
			failed++;
		} finally {
			executed++;
		}
	}

	private Class<?> getClassForName(final String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
}
