package ru.otus.core.model;

import javax.persistence.*;

@Entity(name = "Phone")
@Table(name = "phones")
public class Phone {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "number", nullable = false)
	private String number;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Phone{" +
				"id=" + id +
				", number='" + number + '\'' +
				", user=" + user +
				'}';
	}
}