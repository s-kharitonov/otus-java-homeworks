package ru.otus.appcontainer;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

	private final Map<Class<?>, Object> appComponentsByClass = new HashMap<>();
	private final Map<String, Object> appComponentsByName = new HashMap<>();
	private final Reflections reflections;

	public AppComponentsContainerImpl(final Reflections reflections) {
		this.reflections = reflections;
		processConfig();
	}

	@SuppressWarnings("unchecked")
	private void processConfig() {
		final Map<Class<?>, Set<Method>> methodsByClass = new HashMap<>();
		final Map<Method, Object> configsByMethod = new HashMap<>();
		final Set<Class<?>> configs = reflections.getTypesAnnotatedWith(AppComponentsContainerConfig.class);

		if (configs.isEmpty()) {
			throw new IllegalArgumentException("configuration classes not found!");
		}

		configs.forEach((clazz) -> {
			try {
				final Object configInstance = clazz.getDeclaredConstructor().newInstance();
				final Set<Method> methods = ReflectionUtils.getMethods(
						clazz,
						(method) -> Objects.requireNonNull(method).isAnnotationPresent(AppComponent.class)
				);

				methodsByClass.put(clazz, methods);
				methods.forEach((method) -> {
					configsByMethod.put(method, configInstance);
				});
			} catch (Exception e) {
				throw new IllegalArgumentException("class for configuration must have a default constructor!");
			}
		});

		final List<Method> methods = methodsByClass.keySet().stream()
				.map(methodsByClass::get)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());

		if (methods.isEmpty()) {
			throw new IllegalArgumentException("components not found!");
		}

		sortMethodsByOrder(methods);

		methods.forEach((method) -> {
			final Object owner = configsByMethod.get(method);
			final String componentName = method.getAnnotation(AppComponent.class).name();
			final Class<?> returnType = method.getReturnType();
			final Parameter[] parameters = method.getParameters();
			final Object[] args = Arrays.stream(parameters)
					.map((parameter) -> appComponentsByClass.get(parameter.getType()))
					.toArray();
			final Object result = invokeMethod(owner, method, args);

			appComponentsByClass.put(returnType, result);
			appComponentsByName.put(componentName, result);
		});
	}

	private void sortMethodsByOrder(final List<Method> methods) {
		methods.sort((m1, m2) -> {
			final int order1 = m1.getAnnotation(AppComponent.class).order();
			final int order2 = m2.getAnnotation(AppComponent.class).order();

			return Integer.compare(order1, order2);
		});
	}

	private Object invokeMethod(final Object owner, final Method method, final Object... args) {
		try {
			if (args.length == 0) {
				return method.invoke(owner);
			} else {
				return method.invoke(owner, args);
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("method execution error, method name: %s", method.getName()), e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C> C getAppComponent(final Class<C> componentClass) {
		return (C) appComponentsByClass.get(componentClass);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C> C getAppComponent(final String componentName) {
		return (C) appComponentsByName.get(componentName);
	}
}
