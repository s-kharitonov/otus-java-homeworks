package ru.otus.core.service;

import ru.otus.core.model.User;
import ru.otus.domain.UserDTO;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

	long saveUser(User user);

	Optional<UserDTO> getUser(long id);

	Optional<UserDTO> getFirstUser();

	void createDefaultUserList();

	List<UserDTO> findAllUsers();
}
