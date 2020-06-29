package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AddressDAO;
import ru.otus.core.model.Address;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class AddressServiceImpl implements AddressService {

	private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

	private final AddressDAO addressDAO;

	public AddressServiceImpl(final AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}

	@Override
	public Optional<Address> findById(final long id) {
		try (SessionManager sessionManager = addressDAO.getSessionManager()) {
			sessionManager.beginSession();

			try {
				final Optional<Address> address = addressDAO.findById(id);

				logger.info("address: {}", address.orElse(null));

				return address;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
				return Optional.empty();
			}
		}
	}

	@Override
	public long saveAddress(final Address address) {
		try (SessionManager sessionManager = addressDAO.getSessionManager()) {
			sessionManager.beginSession();

			try {
				addressDAO.insertOrUpdate(address);
				sessionManager.commitSession();

				logger.info("created address: {}", address.getId());

				return address.getId();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sessionManager.rollbackSession();
				return address.getId();
			}
		}
	}
}
