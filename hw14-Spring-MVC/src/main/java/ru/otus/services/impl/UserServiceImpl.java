package ru.otus.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.cache.CacheKeeper;
import ru.otus.dao.UserDAO;
import ru.otus.domain.UserDTO;
import ru.otus.domain.model.User;
import ru.otus.hibernate.SessionManager;
import ru.otus.listeners.Listener;
import ru.otus.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserDAO userDAO;
	private final CacheKeeper<Long, User> cacheKeeper;
	private final Listener<Long, User> listener;

	public UserServiceImpl(final UserDAO userDAO, final CacheKeeper<Long, User> cacheKeeper, final Listener<Long, User> listener) {
		this.userDAO = userDAO;
		this.cacheKeeper = cacheKeeper;
		this.listener = listener;
	}

	@Override
	public Long saveUser(final User user) {
		cacheKeeper.addListener(listener);

		try (SessionManager sessionManager = userDAO.getSessionManager()) {
			sessionManager.beginSession();
			try {
				userDAO.insertUser(user);

				final long userId = user.getId();

				sessionManager.commitSession();
				cacheKeeper.put(userId, user);
				logger.info("created user: {}", userId);

				return userId;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);

				sessionManager.rollbackSession();

				return null;
			}
		} finally {
			cacheKeeper.removeListener(listener);
		}
	}

	@Override
	public UserDTO getUser(final long id) {
		cacheKeeper.addListener(listener);

		final User user = cacheKeeper.get(id);

		if (user != null) {
			return new UserDTO(user);
		}

		try (SessionManager sessionManager = userDAO.getSessionManager()) {

			sessionManager.beginSession();

			try {
				final Optional<User> userOptional = userDAO.findById(id);

				logger.info("user: {}", userOptional.orElse(null));
				userOptional.ifPresent(value -> cacheKeeper.put(id, value));

				return userOptional.map(UserDTO::new).orElse(null);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
			} finally {
				cacheKeeper.removeListener(listener);
			}

			return null;
		}
	}

	@Override
	public List<UserDTO> getAll() {
		try (SessionManager sessionManager = userDAO.getSessionManager()) {
			sessionManager.beginSession();
			try {
				final List<User> users = userDAO.findAll();

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
