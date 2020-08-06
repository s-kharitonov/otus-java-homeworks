package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import ru.otus.cache.CacheKeeper;
import ru.otus.cache.impl.BasicCacheKeeper;
import ru.otus.domain.model.User;
import ru.otus.listeners.Listener;
import ru.otus.listeners.impl.LogEventListener;

import java.nio.charset.StandardCharsets;

@Configuration
@ComponentScan
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

	private final ApplicationContext appContext;

	public AppConfig(final ApplicationContext appContext) {
		this.appContext = appContext;
	}

	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();

		templateResolver.setApplicationContext(appContext);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCacheable(true);
		templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());

		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();

		templateEngine.setTemplateResolver(templateResolver());

		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver viewResolver() {
		final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setOrder(1);
		viewResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());

		return viewResolver;
	}

	@Bean
	public CacheKeeper<Long, User> userCacheKeeper() {
		return new BasicCacheKeeper<>();
	}

	@Bean
	public Listener<Long, User> userEventListener() {
		return new LogEventListener<>();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
	}
}
