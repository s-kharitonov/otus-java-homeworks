package ru.otus;

import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

public class Main {

	public static void main(String[] args) {
		final var sessionFactory = HibernateUtils.buildSessionFactory(
				"hibernate.cfg.xml",
				User.class,
				Address.class,
				Phone.class
		);
		final var sessionManager = new SessionManagerHibernate(sessionFactory);
		final var userService = new DbServiceUserImpl(new UserDaoHibernate(sessionManager));
	}
}
