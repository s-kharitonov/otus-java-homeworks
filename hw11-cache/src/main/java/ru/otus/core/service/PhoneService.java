package ru.otus.core.service;

import ru.otus.core.model.Phone;

import java.util.Optional;

public interface PhoneService {
	Optional<Phone> findById(long id);

	long savePhone(Phone phone);
}
