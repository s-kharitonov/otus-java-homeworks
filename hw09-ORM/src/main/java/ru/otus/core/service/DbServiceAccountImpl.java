package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.model.Account;

import java.util.Optional;

public class DbServiceAccountImpl implements DBServiceAccount {
	private static final Logger logger = LoggerFactory.getLogger(DbServiceAccountImpl.class);

	private final AccountDao accountDao;

	public DbServiceAccountImpl(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public long saveAccount(Account account) {
		try (var sessionManager = accountDao.getSessionManager()) {
			sessionManager.beginSession();
			try {
				var no = accountDao.insertAccount(account);
				sessionManager.commitSession();

				logger.info("created account: {}", no);
				return no;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
				throw new DbServiceException(e);
			}
		}
	}

	@Override
	public Optional<Account> getAccount(long id) {
		try (var sessionManager = accountDao.getSessionManager()) {
			sessionManager.beginSession();
			try {
				Optional<Account> account = accountDao.findById(id);

				logger.info("account: {}", account.orElse(null));
				return account;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
			}
			return Optional.empty();
		}
	}

	@Override
	public void updateAccount(Account account) {
		try (var sessionManager = accountDao.getSessionManager()) {
			sessionManager.beginSession();
			try {
				accountDao.updateAccount(account);
				sessionManager.commitSession();

				logger.info("updated account: {}", account);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
				throw new DbServiceException(e);
			}
		}
	}
}
