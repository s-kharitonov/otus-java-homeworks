package ru.otus.core.dao;

import ru.otus.core.model.Address;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AddressDAO {
	Optional<Address> findById(long id);

	long insertOrUpdate(Address address);

	SessionManager getSessionManager();
}
