package ru.otus.domain.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "User")
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "user_id")
	private long id;

	@NotNull
	@Size(min = 1)
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	@Size(min = 1)
	@Column(name = "login", unique = true, nullable = false)
	private String login;

	@NotNull
	@Size(min = 1)
	@Column(name = "password", nullable = false)
	private String password;

	public User() {
	}

	public User(final String name, final String login, final String password) {
		this.name = name;
		this.login = login;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
