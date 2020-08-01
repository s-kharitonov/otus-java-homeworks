package ru.otus.services.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.cache.CacheKeeper;
import ru.otus.dao.UserDAO;
import ru.otus.domain.UserDTO;
import ru.otus.domain.model.User;
import ru.otus.listeners.Listener;
import ru.otus.services.UserService;
import ru.otus.validators.ObjectValidator;
import ru.otus.validators.impl.GenericValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final SessionFactory sessionFactory;
	private final UserDAO userDAO;
	private final CacheKeeper<Long, User> cacheKeeper;
	private final Listener<Long, User> listener;

	public UserServiceImpl(final SessionFactory sessionFactory,
						   final UserDAO userDAO,
						   final CacheKeeper<Long, User> cacheKeeper,
						   final Listener<Long, User> listener) {
		this.sessionFactory = sessionFactory;
		this.userDAO = userDAO;
		this.cacheKeeper = cacheKeeper;
		this.listener = listener;
	}

	@Override
	public Long saveUser(final User user) {
		cacheKeeper.addListener(listener);

		try (final Session session = sessionFactory.openSession()) {
			final Transaction transaction = session.getTransaction();

			try {
				transaction.begin();
				userDAO.insertUser(user);

				final Long userId = user.getId();

				cacheKeeper.put(userId, user);
				transaction.commit();

				logger.info("created user: {}", userId);
				return userId;
			} catch (Exception e) {
				transaction.rollback();
				logger.error(e.getMessage(), e);
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

		try (final Session session = sessionFactory.openSession()) {
			final Transaction transaction = session.getTransaction();

			try {
				transaction.begin();
				final Optional<User> userOptional = userDAO.findById(id);

				logger.info("user: {}", userOptional.orElse(null));
				userOptional.ifPresent(value -> cacheKeeper.put(id, value));

				transaction.commit();

				return userOptional.map(UserDTO::new).orElse(null);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				transaction.rollback();
			} finally {
				cacheKeeper.removeListener(listener);
			}

			return null;
		}
	}

	@Override
	public List<UserDTO> getAllUsers() {
		try (final Session session = sessionFactory.openSession()) {
			final Transaction transaction = session.getTransaction();
			try {
				transaction.begin();
				final List<User> users = userDAO.findAll();

				logger.info("users: {}", users);

				transaction.commit();

				return users.stream()
						.map(UserDTO::new)
						.collect(Collectors.toList());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				transaction.rollback();
			}
			return new ArrayList<>();
		}
	}

	@Override
	public ResponseEntity<?> saveUserForResponse(final UserDTO user) {
		final ObjectValidator<UserDTO> validator = new GenericValidator<>();

		if (!validator.isValid(user)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		final User userEntity = new User(
				user.getName(),
				user.getLogin(),
				user.getPassword()
		);
		final Long userId = saveUser(userEntity);

		if (userId == null) {
			return new ResponseEntity<>("user creation error!", HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<>(userId, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getUserForResponse(final long id) {
		final UserDTO user = getUser(id);

		if (user == null) {
			return new ResponseEntity<>("user not found!", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getAllForResponse() {
		final List<UserDTO> users = getAllUsers();

		if (users.isEmpty()) {
			return new ResponseEntity<>("users not found!", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
	}
}
