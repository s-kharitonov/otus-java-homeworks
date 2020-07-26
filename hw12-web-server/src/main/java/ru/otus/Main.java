package ru.otus;

import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import ru.otus.core.model.User;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.server.UsersWebServerWithBasicSecurity;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.validators.impl.GenericValidator;

import javax.validation.Validation;

public class Main {
	private static final int WEB_SERVER_PORT = 8080;
	private static final String TEMPLATES_DIR = "/templates/";
	private static final String REALM_NAME = "AnyRealm";
	private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";

	public static void main(String[] args) throws Exception {
		final var sessionFactory = HibernateUtils.buildSessionFactory(
				"hibernate.cfg.xml",
				User.class
		);
		final var validator = Validation.byDefaultProvider()
				.configure()
				.messageInterpolator(new ParameterMessageInterpolator())
				.buildValidatorFactory().getValidator();
		final var sessionManager = new SessionManagerHibernate(sessionFactory);
		final var userService = new DbServiceUserImpl(new UserDaoHibernate(sessionManager), new GenericValidator<>(validator));

		userService.createDefaultUserList();

		final var gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		final var templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
		final String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
		final LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);
		final var usersWebServer = new UsersWebServerWithBasicSecurity(
				WEB_SERVER_PORT,
				loginService,
				userService,
				gson,
				templateProcessor
		);

		usersWebServer.start();
		usersWebServer.join();
	}
}
