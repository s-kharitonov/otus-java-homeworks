package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.domain.UserDTO;
import ru.otus.validators.ObjectValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DbServiceUserImpl implements DBServiceUser {
	private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

	private final UserDao userDao;
	private final ObjectValidator<User> validator;

	public DbServiceUserImpl(final UserDao userDao, final ObjectValidator<User> validator) {
		this.userDao = userDao;
		this.validator = validator;
	}

	@Override
	public long saveUser(User user) {

		if (!validator.isValid(user)) {
			throw new DbServiceException("user fields is not valid!");
		}

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
	public Optional<UserDTO> getUser(long id) {
		try (SessionManager sessionManager = userDao.getSessionManager()) {
			sessionManager.beginSession();
			try {
				Optional<User> userOptional = userDao.findById(id);

				logger.info("user: {}", userOptional.orElse(null));

				return userOptional.map(UserDTO::new);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
			}
			return Optional.empty();
		}
	}

	@Override
	public Optional<UserDTO> getFirstUser() {
		try (SessionManager sessionManager = userDao.getSessionManager()) {
			sessionManager.beginSession();
			try {
				Optional<User> userOptional = userDao.findFirst();

				logger.info("user: {}", userOptional.orElse(null));
				return userOptional.map(UserDTO::new);
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
	public List<UserDTO> findAllUsers() {
		try (SessionManager sessionManager = userDao.getSessionManager()) {
			sessionManager.beginSession();
			try {
				final List<User> users = userDao.findAllUsers();

				logger.info("users: {}", users);
				return users.stream()
						.map(UserDTO::new)
						.collect(Collectors.toList());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
			}
			return new ArrayList<>();
		}
	}
}
