package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
	private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

	private final UserDao userDao;

	public DbServiceUserImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public long saveUser(User user) {
		try (SessionManager sessionManager = userDao.getSessionManager()) {
			sessionManager.beginSession();
			try {
				userDao.insertOrUpdate(user);
				long userId = user.getId();
				sessionManager.commitSession();

				logger.info("created user: {}", userId);
				return userId;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
				throw new DbServiceException(e);
			}
		}
	}


	@Override
	public Optional<User> getUser(long id) {
		try (SessionManager sessionManager = userDao.getSessionManager()) {
			sessionManager.beginSession();
			try {
				Optional<User> userOptional = userDao.findById(id);

				logger.info("user: {}", userOptional.orElse(null));
				return userOptional;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
			}
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> getFirstUser() {
		try (SessionManager sessionManager = userDao.getSessionManager()) {
			sessionManager.beginSession();
			try {
				Optional<User> userOptional = userDao.findFirst();

				logger.info("user: {}", userOptional.orElse(null));
				return userOptional;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
			}
			return Optional.empty();
		}
	}

	@Override
	public void createDefaultUserList() {
		for (int i = 1; i < 10; i++) {
			saveUser(
					new User(
							"user_" + i,
							"user_" + i,
							"user_" + i
					)
			);
		}
	}

	@Override
	public List<User> findAllUsers() {
		try (SessionManager sessionManager = userDao.getSessionManager()) {
			sessionManager.beginSession();
			try {
				List<User> users = userDao.findAllUsers();

				logger.info("users: {}", users);
				return users;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
			}
			return new ArrayList<>();
		}
	}
}
