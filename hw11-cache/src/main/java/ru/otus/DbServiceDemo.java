package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cache.HwCache;
import ru.otus.cache.HwListener;
import ru.otus.cache.impl.LogEventListener;
import ru.otus.cache.impl.MyCache;
import ru.otus.core.dao.AddressDAO;
import ru.otus.core.dao.PhoneDAO;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.service.*;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.AddressDAOHibernate;
import ru.otus.hibernate.dao.PhoneDAOHibernate;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DbServiceDemo {
	private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

	public static void main(String[] args) {
		final SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(
				"hibernate.cfg.xml",
				User.class,
				Address.class,
				Phone.class
		);

		final SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
		final UserDao userDao = new UserDaoHibernate(sessionManager);
		final HwCache<Long, User> userCache = new MyCache<>();
		final HwListener<Long, User> userLogListener = new LogEventListener<>();
		final DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao, userCache, userLogListener);
		final AddressDAO addressDAO = new AddressDAOHibernate(sessionManager);
		final PhoneDAO phoneDAO = new PhoneDAOHibernate(sessionManager);
		final AddressService addressService = new AddressServiceImpl(addressDAO);
		final PhoneService phoneService = new PhoneServiceImpl(phoneDAO);

		final var user = new User(
				"Вася",
				new Address("street1"));

		user.setPhones(Arrays.asList(
				new Phone("1", user),
				new Phone("2", user),
				new Phone("3", user)
		));

		final long id = dbServiceUser.saveUser(user);
		final Optional<User> createdUser = dbServiceUser.getUser(id);
		final Address createdAddress = createdUser.orElseThrow().getAddress();
		final List<Phone> createdPhones = createdUser.orElseThrow().getPhones();

		logger.info("created user: {}", createdUser.orElse(null));
		logger.info("created address: {}", addressService.findById(createdAddress.getId()).orElse(null));

		createdPhones.forEach((phone) -> {
			logger.info("created phone: {}", phoneService.findById(phone.getId()).orElse(null));
		});
	}
}
