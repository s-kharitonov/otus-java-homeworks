package ru.otus.core.model;


import javax.persistence.*;
import java.util.List;

@Entity(name = "User")
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "user_id")
	private long id;

	@Column(name = "name")
	private String name;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<Phone> phones;

	public User() {
	}

	public User(final String name, final Address address) {
		this.name = name;
		this.address = address;
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

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(final List<Phone> phones) {
		this.phones = phones;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", address=" + address +
				", phones=" + phones +
				'}';
	}
}
