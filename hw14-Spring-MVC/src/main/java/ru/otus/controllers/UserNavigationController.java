package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.domain.UserDTO;
import ru.otus.services.UserService;

import java.util.List;

@Controller
public class UserNavigationController {

	private final UserService userService;

	public UserNavigationController(final UserService userService) {
		this.userService = userService;
	}

	@GetMapping(path = "/")
	public String getUsersPage(Model model) {
		final List<UserDTO> users = userService.getAllUsers();

		model.addAttribute("users", users);

		return "users.html";
	}
}
