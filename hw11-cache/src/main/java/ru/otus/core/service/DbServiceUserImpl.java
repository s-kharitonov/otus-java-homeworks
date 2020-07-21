package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cache.HwCache;
import ru.otus.cache.HwListener;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
	private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

	private final UserDao userDao;
	private final HwCache<Long, User> cache;
	private final HwListener<Long, User> listener;

	public DbServiceUserImpl(UserDao userDao, final HwCache<Long, User> cache, final HwListener<Long, User> listener) {
		this.userDao = userDao;
		this.cache = cache;
		this.listener = listener;
	}

	@Override
	public long saveUser(User user) {
		cache.addListener(listener);

		try (SessionManager sessionManager = userDao.getSessionManager()) {
			sessionManager.beginSession();
			try {
				userDao.insertOrUpdate(user);
				long userId = user.getId();
				sessionManager.commitSession();

				cache.put(userId, user);

				logger.info("created user: {}", userId);
				return userId;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
				throw new DbServiceException(e);
			} finally {
				cache.removeListener(listener);
			}
		}
	}


	@Override
	public Optional<User> getUser(long id) {
		cache.addListener(listener);

		final User user = cache.get(id);

		if (user != null) {
			return Optional.of(user);
		}

		try (SessionManager sessionManager = userDao.getSessionManager()) {
			sessionManager.beginSession();

			try {
				Optional<User> userOptional = userDao.findById(id);

				logger.info("user: {}", userOptional.orElse(null));

				userOptional.ifPresent(value -> cache.put(id, value));

				return userOptional;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
			} finally {
				cache.removeListener(listener);
			}
			return Optional.empty();
		}
	}
}
