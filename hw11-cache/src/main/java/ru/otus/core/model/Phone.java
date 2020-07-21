package ru.otus.core.model;

import javax.persistence.*;

@Entity(name = "Phone")
@Table(name = "phones")
public class Phone {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "phone_id")
	private long id;

	@Column(name = "number", nullable = false)
	private String number;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Phone() {
	}

	public Phone(final String number, final User user) {
		this.number = number;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Phone{" +
				"id=" + id +
				", number='" + number + '\'' +
				'}';
	}
}