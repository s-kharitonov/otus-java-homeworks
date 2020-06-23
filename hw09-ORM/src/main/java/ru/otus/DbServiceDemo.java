package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.Account;
import ru.otus.core.model.User;
import ru.otus.core.service.DbServiceAccountImpl;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.AccountDaoJdbc;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.mapper.impl.BasicEntityClassMetaData;
import ru.otus.jdbc.mapper.impl.BasicEntitySQLMetaData;
import ru.otus.jdbc.mapper.impl.BasicJdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbServiceDemo {
	private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

	public static void main(String[] args) throws Exception {
		var dataSource = new DataSourceH2();
		var demo = new DbServiceDemo();

		demo.createTable(dataSource, "create table user(id bigint(20) auto_increment, name varchar(255), age int(3))");
		demo.createTable(dataSource, "create table account(no bigint(20) auto_increment, type varchar(255), rest number)");

		final var sessionManager = new SessionManagerJdbc(dataSource);
		final DbExecutorImpl<User> userDbExecutor = new DbExecutorImpl<>();
		final DbExecutorImpl<Account> accountDbExecutor = new DbExecutorImpl<>();
		final EntityClassMetaData<User> userClassMetaData = new BasicEntityClassMetaData<>(User.class);
		final EntityClassMetaData<Account> accountClassMetaData = new BasicEntityClassMetaData<>(Account.class);
		final JdbcMapper<User> userJdbcMapper = new BasicJdbcMapper<>(
				userClassMetaData,
				new BasicEntitySQLMetaData<>(userClassMetaData),
				userDbExecutor,
				sessionManager
		);
		final JdbcMapper<Account> accountJdbcMapper = new BasicJdbcMapper<>(
				accountClassMetaData,
				new BasicEntitySQLMetaData<>(accountClassMetaData),
				accountDbExecutor,
				sessionManager
		);
		var userDao = new UserDaoJdbc(sessionManager, userJdbcMapper);
		var accountDao = new AccountDaoJdbc(sessionManager, accountJdbcMapper);

		var dbServiceUser = new DbServiceUserImpl(userDao);
		var dbServiceAccount = new DbServiceAccountImpl(accountDao);
		var id = dbServiceUser.saveUser(new User(0, "dbServiceUser", 1));
		var accountId = dbServiceAccount.saveAccount(new Account(0, "basic", BigDecimal.ONE));

		dbServiceUser.updateUser(new User(0, "userName", 18));
		dbServiceAccount.updateAccount(new Account(0, "custom", BigDecimal.TEN));

		Optional<User> user = dbServiceUser.getUser(id);
		Optional<Account> account = dbServiceAccount.getAccount(accountId);

		user.ifPresentOrElse(
				crUser -> logger.info("created user, name:{}", crUser.getName()),
				() -> logger.info("user was not created")
		);

		account.ifPresentOrElse(
				crAccount -> logger.info("created account, account:{}", crAccount),
				() -> logger.info("account was not created")
		);
	}

	private void createTable(DataSource dataSource, String query) throws SQLException {
		try (var connection = dataSource.getConnection();
			 var pst = connection.prepareStatement(query);) {
			pst.executeUpdate();
		}
		System.out.println("table created");
	}
}
