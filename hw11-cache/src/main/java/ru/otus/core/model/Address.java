package ru.otus.core.model;

import javax.persistence.*;

@Entity(name = "Address")
@Table(name = "addresses")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "address_id")
	private long id;

	@Column(name = "street", nullable = false)
	private String street;

	public Address() {
	}

	public Address(final String street) {
		this.street = street;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	@Override
	public String toString() {
		return "Address{" +
				"id=" + id +
				", street='" + street + '\'' +
				'}';
	}
}
