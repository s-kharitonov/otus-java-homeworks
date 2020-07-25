package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.PhoneDAO;
import ru.otus.core.model.Phone;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class PhoneServiceImpl implements PhoneService {

	private static final Logger logger = LoggerFactory.getLogger(PhoneServiceImpl.class);

	private final PhoneDAO phoneDAO;

	public PhoneServiceImpl(final PhoneDAO phoneDAO) {
		this.phoneDAO = phoneDAO;
	}

	@Override
	public Optional<Phone> findById(final long id) {
		try (SessionManager sessionManager = phoneDAO.getSessionManager()) {
			sessionManager.beginSession();

			try {
				final Optional<Phone> phone = phoneDAO.findById(id);

				logger.info("phone: {}", phone.orElse(null));

				return phone;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
				return Optional.empty();
			}
		}
	}

	@Override
	public long savePhone(final Phone phone) {
		try (SessionManager sessionManager = phoneDAO.getSessionManager()) {
			sessionManager.beginSession();

			try {
				phoneDAO.insertOrUpdate(phone);
				sessionManager.commitSession();

				logger.info("created phone: {}", phone.getId());

				return phone.getId();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
				return phone.getId();
			}
		}
	}
}
