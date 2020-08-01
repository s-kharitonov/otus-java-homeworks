package ru.otus.dao;

import ru.otus.domain.model.User;
import ru.otus.hibernate.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
	Optional<User> findById(long id);

	long insertUser(User user);

	List<User> findAll();

	SessionManager getSessionManager();
}
