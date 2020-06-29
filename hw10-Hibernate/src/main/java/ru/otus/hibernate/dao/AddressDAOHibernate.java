package ru.otus.hibernate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AddressDAO;
import ru.otus.core.model.Address;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class AddressDAOHibernate implements AddressDAO {

	private static final Logger logger = LoggerFactory.getLogger(AddressDAOHibernate.class);

	private final SessionManagerHibernate sessionManager;

	public AddressDAOHibernate(final SessionManagerHibernate sessionManager) {
		this.sessionManager = sessionManager;
	}

	@Override
	public Optional<Address> findById(final long id) {
		try {
			final var session = sessionManager.getCurrentSession().getHibernateSession();

			return Optional.ofNullable(session.find(Address.class, id));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Override
	public long insertOrUpdate(final Address address) {
		try {
			final var session = sessionManager.getCurrentSession().getHibernateSession();

			if (address.getId() > 0) {
				session.merge(address);
			} else {
				session.persist(address);
				session.flush();
			}

			return address.getId();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return address.getId();
		}
	}

	@Override
	public SessionManager getSessionManager() {
		return sessionManager;
	}
}
