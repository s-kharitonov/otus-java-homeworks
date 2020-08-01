package ru.otus.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.dao.UserDAO;
import ru.otus.domain.model.User;
import ru.otus.hibernate.SessionManager;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	@Override
	public Optional<User> findById(final long id) {
		return Optional.empty();
	}

	@Override
	public long insertUser(final User user) {
		return 0;
	}

	@Override
	public List<User> findAll() {
		return null;
	}

	@Override
	public SessionManager getSessionManager() {
		return null;
	}
}
