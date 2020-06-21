package ru.otus.jdbc.dao;


import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.mapper.impl.BasicEntityClassMetaData;
import ru.otus.jdbc.mapper.impl.BasicEntitySQLMetaData;
import ru.otus.jdbc.mapper.impl.BasicJdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class UserDaoJdbc implements UserDao {
	private final SessionManagerJdbc sessionManager;
	private final DbExecutor<User> executor;

	public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutorImpl<User> dbExecutor) {
		this.sessionManager = sessionManager;
		this.executor = dbExecutor;
	}

	@Override
	public Optional<User> findById(long id) {
		final var mapper = getJdbcMapper();

		return Optional.ofNullable(mapper.findById(id, User.class));
	}

	@Override
	public long insertUser(User user) {
		final var mapper = getJdbcMapper();

		mapper.insert(user);

		return user.getId();
	}

	@Override
	public void updateUser(final User user) {
		final var mapper = getJdbcMapper();

		mapper.update(user);
	}

	@Override
	public void insertOrUpdate(final User user) {
		final var mapper = getJdbcMapper();

		mapper.insertOrUpdate(user);
	}

	@Override
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	private JdbcMapper<User> getJdbcMapper() {
		final EntityClassMetaData<User> classMetaData = new BasicEntityClassMetaData<>(User.class);
		return new BasicJdbcMapper<>(classMetaData, new BasicEntitySQLMetaData<>(classMetaData), executor, sessionManager);
	}
}
