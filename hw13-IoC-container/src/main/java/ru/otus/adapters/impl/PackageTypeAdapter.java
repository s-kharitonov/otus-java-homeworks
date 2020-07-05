package ru.otus.adapters.impl;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import ru.otus.adapters.ReflectionsTypeAdapter;

public class PackageTypeAdapter implements ReflectionsTypeAdapter {
	private final String packageName;

	public PackageTypeAdapter(final String packageName) {
		this.packageName = packageName;
	}

	@Override
	public Reflections getReflections() {
		return new Reflections(new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forPackage(packageName))
				.filterInputsBy(new FilterBuilder().includePackage(packageName))
				.setScanners(
						new TypeAnnotationsScanner(),
						new MethodAnnotationsScanner(),
						new SubTypesScanner().filterResultsBy(new FilterBuilder().includePackage(packageName))
				));
	}
}
