package ru.otus.proxy;

import ru.otus.annotations.Log;
import ru.otus.calculators.Calculator;
import ru.otus.domain.Constants;
import ru.otus.utils.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class IOC {

	private static final Set<Class<? extends Calculator>> calculatorsForLogging = new HashSet<>();

	public static Calculator createCalculator(final Calculator calculator) {
		final InvocationHandler handler = new LogInvocationHandler(calculator);
		final Class<? extends Calculator> clazz = calculator.getClass();
		final Method[] methods = clazz.getMethods();
		final Set<Method> loggedMethods = ReflectionUtils.filterMethodsByAnnotation(methods, Log.class);

		if (!loggedMethods.isEmpty()) {
			calculatorsForLogging.add(clazz);
		}

		return (Calculator) Proxy.newProxyInstance(IOC.class.getClassLoader(), new Class<?>[]{Calculator.class}, handler);
	}

	private static void printMethodInfo(final Calculator calculator, final Method method, final Object[] args) throws Exception {
		final String methodName = method.getName();

		System.out.println(Constants.LOGGER_START_MSG);
		System.out.println(String.format(Constants.METHOD_INFO_TEMPLATE_MSG, methodName, Arrays.toString(args)));
		System.out.println(String.format(Constants.METHOD_RESULT_MSG, method.invoke(calculator, args)));
		System.out.println(Constants.LOGGER_FINISH_MSG);
	}


	private static class LogInvocationHandler implements InvocationHandler {

		private final Calculator calculator;

		private LogInvocationHandler(final Calculator calculator) {
			this.calculator = calculator;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			final Class<? extends Calculator> clazz = calculator.getClass();

			if (calculatorsForLogging.contains(clazz)) {
				printMethodInfo(calculator, method, args);
			}

			return method.invoke(calculator, args);
		}
	}
}
