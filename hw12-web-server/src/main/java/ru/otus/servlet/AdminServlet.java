package ru.otus.servlet;

import ru.otus.core.service.DBServiceUser;
import ru.otus.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {

	private static final String PAGE_TEMPLATE = "admin.html";
	private static final String TEMPLATE_ATTR_USERS = "users";

	private final DBServiceUser userService;
	private final TemplateProcessor templateProcessor;

	public AdminServlet(TemplateProcessor templateProcessor, DBServiceUser userService) {
		this.templateProcessor = templateProcessor;
		this.userService = userService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
		Map<String, Object> paramsMap = new HashMap<>();

		paramsMap.put(TEMPLATE_ATTR_USERS, userService.findAllUsers());

		response.setContentType("text/html");
		response.getWriter().println(templateProcessor.getPage(PAGE_TEMPLATE, paramsMap));
	}
}
