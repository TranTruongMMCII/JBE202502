package vn.edu.r2s.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import vn.edu.r2s.model.User;

public interface UserService {

	List<User> getAll();

	Optional<User> getById(final Integer id);

	User save(final User newUser);

	User update(final User newUser);

	boolean delete(final User deletedUser);

	boolean delete(final Integer id);
	
	List<User> search(final String name);

	List<User> search(final String name, final Pageable pageable);

	Boolean signUp(final User user);
}
