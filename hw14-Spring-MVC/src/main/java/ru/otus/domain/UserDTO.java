package ru.otus.domain;

import ru.otus.domain.model.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {
	private long id;

	@NotNull
	@Size(min = 1)
	private String name;

	@NotNull
	@Size(min = 1)
	private String login;

	@NotNull
	@Size(min = 1)
	private String password;

	public UserDTO() {
	}

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

	public void setId(final long id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public void setPassword(final String password) {
		this.password = password;
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
