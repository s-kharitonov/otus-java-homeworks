package ru.otus.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.dao.UserDAO;
import ru.otus.domain.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	private final SessionFactory sessionFactory;

	public UserDAOImpl(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Optional<User> findById(final long id) {
		try {
			final Session session = sessionFactory.getCurrentSession();

			return Optional.ofNullable(session.find(User.class, id));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Override
	public Long insertUser(final User user) {
		try {
			final Session session = sessionFactory.getCurrentSession();

			session.persist(user);
			session.flush();

			return user.getId();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public List<User> findAll() {
		try {
			final Session session = sessionFactory.getCurrentSession();
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
}
