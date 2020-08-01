package ru.otus.domain;

import ru.otus.domain.model.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {
	private final long id;

	@NotNull
	@Size(min = 1)
	private final String name;

	@NotNull
	@Size(min = 1)
	private final String login;

	@NotNull
	@Size(min = 1)
	private final String password;

	public UserDTO(final User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.login = user.getLogin();
		this.password = user.getPassword();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "UserDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
