package ru.otus.jdbc.dao;


import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class UserDaoJdbc implements UserDao {
	private final SessionManagerJdbc sessionManager;
	private final JdbcMapper<User> mapper;

	public UserDaoJdbc(SessionManagerJdbc sessionManager, JdbcMapper<User> mapper) {
		this.sessionManager = sessionManager;
		this.mapper = mapper;
	}

	@Override
	public Optional<User> findById(long id) {
		return Optional.ofNullable(mapper.findById(id, User.class));
	}

	@Override
	public long insertUser(User user) {
		mapper.insert(user);
		return user.getId();
	}

	@Override
	public void updateUser(final User user) {
		mapper.update(user);
	}

	@Override
	public void insertOrUpdate(final User user) {
		mapper.insertOrUpdate(user);
	}

	@Override
	public SessionManager getSessionManager() {
		return sessionManager;
	}
}
