package ru.otus.core.service;

import ru.otus.core.model.Address;

import java.util.Optional;

public interface AddressService {
	Optional<Address> findById(long id);

	long saveAddress(Address address);
}
