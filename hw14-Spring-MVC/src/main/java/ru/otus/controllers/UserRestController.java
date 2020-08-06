package ru.otus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.UserDTO;
import ru.otus.services.UserService;

@RestController
public class UserRestController {

	private final UserService userService;

	public UserRestController(final UserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "/user")
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) {
		return userService.saveUserForResponse(user);
	}

	@GetMapping(value = "/user/{id}")
	public ResponseEntity<?> getUser(@PathVariable long id) {
		return userService.getUserForResponse(id);
	}

	@GetMapping(value = "/user")
	public ResponseEntity<?> getUsers() {
		return userService.getAllForResponse();
	}
}
