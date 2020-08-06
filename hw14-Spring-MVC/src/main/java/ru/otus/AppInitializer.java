package ru.otus;

import org.springframework.lang.NonNull;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import java.nio.charset.StandardCharsets;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{AppConfig.class};
	}

	@Override
	@NonNull
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();

		encodingFilter.setEncoding(StandardCharsets.UTF_8.name());
		encodingFilter.setForceEncoding(true);

		return new Filter[]{encodingFilter};
	}

	@Override
	@NonNull
	protected DispatcherServlet createDispatcherServlet(@NonNull WebApplicationContext servletAppContext) {
		final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);

		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

		return dispatcherServlet;
	}
}
