package ru.otus.dao;

import ru.otus.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
	Optional<User> findById(long id);

	Long insertUser(User user);

	List<User> findAll();
}
