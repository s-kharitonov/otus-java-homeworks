package ru.otus.core.model;


import javax.persistence.*;
import java.util.List;

@Entity(name = "User")
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Address.class)
	@JoinColumn(name = "address_id")
	private String street;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = Phone.class)
	@JoinColumn(name = "phone_id")
	private List<Phone> phones;

	public User() {
	}

	public User(long id, String name) {
		this.id = id;
		this.name = name;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(final List<Phone> phones) {
		this.phones = phones;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", street='" + street + '\'' +
				", phones=" + phones +
				'}';
	}
}
