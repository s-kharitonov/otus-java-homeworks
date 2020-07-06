package ru.otus.adapters.impl;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import ru.otus.adapters.ReflectionsAdapter;

public class ClassAdapter implements ReflectionsAdapter {

	private final Class<?>[] classes;

	public ClassAdapter(final Class<?>... classes) {
		this.classes = classes;
	}

	@Override
	public Reflections getReflections() {
		final var configBuilder = new ConfigurationBuilder();
		final var filter = new FilterBuilder();

		for (Class<?> clazz : classes) {
			configBuilder.addUrls(ClasspathHelper.forClass(clazz));
			filter.includePackage(clazz);
		}

		configBuilder.setScanners(new TypeAnnotationsScanner(), new MethodAnnotationsScanner(), new SubTypesScanner());

		return new Reflections(configBuilder);
	}
}
