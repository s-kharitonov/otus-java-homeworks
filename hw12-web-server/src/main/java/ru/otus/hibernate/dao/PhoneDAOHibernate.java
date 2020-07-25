package ru.otus.hibernate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.PhoneDAO;
import ru.otus.core.model.Phone;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class PhoneDAOHibernate implements PhoneDAO {

	private static final Logger logger = LoggerFactory.getLogger(PhoneDAOHibernate.class);

	private final SessionManagerHibernate sessionManager;

	public PhoneDAOHibernate(final SessionManagerHibernate sessionManager) {
		this.sessionManager = sessionManager;
	}

	@Override
	public Optional<Phone> findById(final long id) {
		try {
			final var session = sessionManager.getCurrentSession().getHibernateSession();

			return Optional.ofNullable(session.find(Phone.class, id));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Override
	public long insertOrUpdate(final Phone phone) {
		try {
			final var session = sessionManager.getCurrentSession().getHibernateSession();

			if (phone.getId() > 0) {
				session.merge(phone);
			} else {
				session.persist(phone);
				session.flush();
			}

			return phone.getId();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return phone.getId();
		}
	}

	@Override
	public SessionManager getSessionManager() {
		return sessionManager;
	}
}
