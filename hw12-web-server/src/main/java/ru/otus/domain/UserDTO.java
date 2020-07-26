package ru.otus.domain;

import ru.otus.core.model.User;

public class UserDTO {
	private final long id;
	private final String name;
	private final String login;
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
