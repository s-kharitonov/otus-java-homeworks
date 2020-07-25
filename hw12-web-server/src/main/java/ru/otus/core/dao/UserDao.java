package ru.otus.core.dao;

import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {
	Optional<User> findById(long id);

	Optional<User> findFirst();

	List<User> findAllUsers();

	void insertOrUpdate(User user);

	SessionManager getSessionManager();
}
