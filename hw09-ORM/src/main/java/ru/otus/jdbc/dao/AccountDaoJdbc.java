package ru.otus.jdbc.dao;

import ru.otus.core.dao.AccountDao;
import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {

	private final SessionManagerJdbc sessionManager;
	private final JdbcMapper<Account> mapper;

	public AccountDaoJdbc(SessionManagerJdbc sessionManager, JdbcMapper<Account> mapper) {
		this.sessionManager = sessionManager;
		this.mapper = mapper;
	}

	@Override
	public Optional<Account> findById(long id) {
		return Optional.ofNullable(mapper.findById(id, Account.class));
	}

	@Override
	public long insertAccount(Account account) {
		mapper.insert(account);
		return account.getNo();
	}

	@Override
	public void updateAccount(final Account account) {
		mapper.update(account);
	}

	@Override
	public void insertOrUpdate(final Account account) {
		mapper.insertOrUpdate(account);
	}

	@Override
	public SessionManager getSessionManager() {
		return sessionManager;
	}
}
