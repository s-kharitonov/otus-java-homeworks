package ru.otus.core.dao;

import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
	Optional<Account> findById(long id);

	long insertAccount(Account user);

	void updateAccount(Account user);

	void insertOrUpdate(Account user);

	SessionManager getSessionManager();
}
