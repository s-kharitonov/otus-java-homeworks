package ru.otus.core.dao;

import ru.otus.core.model.Phone;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface PhoneDAO {
	Optional<Phone> findById(long id);

	long insertOrUpdate(Phone phone);

	SessionManager getSessionManager();
}
