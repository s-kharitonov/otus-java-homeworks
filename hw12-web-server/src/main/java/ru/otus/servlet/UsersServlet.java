package ru.otus.servlet;

import ru.otus.core.service.DBServiceUser;
import ru.otus.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class UsersServlet extends HttpServlet {

	private static final String USERS_PAGE_TEMPLATE = "users.html";
	private static final String TEMPLATE_ATTR_RANDOM_USER = "randomUser";

	private final DBServiceUser userService;
	private final TemplateProcessor templateProcessor;

	public UsersServlet(TemplateProcessor templateProcessor, DBServiceUser userService) {
		this.templateProcessor = templateProcessor;
		this.userService = userService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
		Map<String, Object> paramsMap = new HashMap<>();

		userService.getFirstUser().ifPresent(firstUser -> paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, firstUser));

		response.setContentType("text/html");
		response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
	}

}
