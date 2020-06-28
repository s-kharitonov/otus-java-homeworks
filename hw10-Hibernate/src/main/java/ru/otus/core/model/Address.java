package ru.otus.core.model;

import javax.persistence.*;

@Entity(name = "Address")
@Table(name = "addresses")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "street", nullable = false)
	private String street;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Address{" +
				"id=" + id +
				", street='" + street + '\'' +
				", user=" + user +
				'}';
	}
}
