package ru.otus.hibernate.dao;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoHibernate implements UserDao {
	private static final Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

	private final SessionManagerHibernate sessionManager;

	public UserDaoHibernate(SessionManagerHibernate sessionManager) {
		this.sessionManager = sessionManager;
	}


	@Override
	public Optional<User> findById(long id) {
		DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
		try {
			return Optional.ofNullable(currentSession.getHibernateSession().find(User.class, id));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return Optional.empty();
	}

	@Override
	public Optional<User> findFirst() {
		try {
			final Session session = sessionManager.getCurrentSession().getHibernateSession();
			final CriteriaBuilder builder = session.getCriteriaBuilder();
			final CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
			final Root<User> root = criteriaQuery.from(User.class);
			final CriteriaQuery<User> usersQuery = criteriaQuery.select(root);
			final Query<User> query = session.createQuery(usersQuery)
					.setFirstResult(0)
					.setMaxResults(1);

			return Optional.ofNullable(query.getSingleResult());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Override
	public List<User> findAllUsers() {
		try {
			final Session session = sessionManager.getCurrentSession().getHibernateSession();
			final CriteriaBuilder builder = session.getCriteriaBuilder();
			final CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
			final Root<User> root = criteriaQuery.from(User.class);
			final CriteriaQuery<User> usersQuery = criteriaQuery.select(root);

			return session.createQuery(usersQuery).getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	public void insertOrUpdate(User user) {
		DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
		try {
			Session hibernateSession = currentSession.getHibernateSession();
			if (user.getId() > 0) {
				hibernateSession.merge(user);
			} else {
				hibernateSession.persist(user);
				hibernateSession.flush();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UserDaoException(e);
		}
	}


	@Override
	public SessionManager getSessionManager() {
		return sessionManager;
	}
}
