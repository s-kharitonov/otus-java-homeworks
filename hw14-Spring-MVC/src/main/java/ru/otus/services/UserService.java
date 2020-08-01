package ru.otus.services;

import ru.otus.domain.UserDTO;
import ru.otus.domain.model.User;

import java.util.List;

public interface UserService {
	Long saveUser(User user);

	UserDTO getUser(long id);

	List<UserDTO> getAll();
}
