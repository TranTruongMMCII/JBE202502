package vn.edu.r2s.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.edu.r2s.constant.SecurityRole;
import vn.edu.r2s.exception.UserAlreadyExistException;
import vn.edu.r2s.exception.UserNotFoundException;
import vn.edu.r2s.model.User;
import vn.edu.r2s.repository.RoleRepository;
import vn.edu.r2s.repository.UserRepository;
import vn.edu.r2s.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;

	@Override
	public List<User> getAll() {
		return this.userRepository.findAll();
	}

	@Override
	public Optional<User> getById(Integer id) {
		return this.userRepository.findById(id);
	}

	@Override
	public User save(User newUser) {
		return this.userRepository.save(newUser);
	}

	@Override
	public User update(User newUser) {
		// b1: tim user voi id
		User foundUser = this.userRepository.findById(newUser.getId())
				.orElseThrow(() -> new UserNotFoundException("Cannot found user with id: " + newUser.getId()));
//		Optional<User> optionalUser = this.userRepository.findById(newUser.getId());
//		if (optionalUser.isEmpty()) {
//			throw new UserNotFoundException("Cannot found user with id: " + newUser.getId());
//		}
//		User foundUser = optionalUser.get();

		// b2: update new value
		foundUser.setEmail(newUser.getEmail());
		foundUser.setName(newUser.getName());

		// b3: update
		return this.userRepository.save(foundUser);
	}

	@Override
	public boolean delete(User deletedUser) {
		// b1: tim user voi id
//		Optional<User> optionalUser = this.userRepository.findById(deletedUser.getId());
//		if (optionalUser.isEmpty()) {
//			System.err.println("not found");
//			return false;
//		}
//		User foundUser = optionalUser.get();
		User foundUser = this.userRepository.findById(deletedUser.getId())
				.orElseThrow(() -> new UserNotFoundException("Cannot found user with id: " + deletedUser.getId()));

		// b2: update new value
		foundUser.setDeleted(true);

		// b3: update
		return this.userRepository.save(foundUser).getDeleted();
	}

	@Override
	public boolean delete(Integer id) {
		// b1: tim user voi id
		Optional<User> optionalUser = this.userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			System.err.println("not found");
			return false;
		}

//		this.userRepository.deleteById(id);
//		return true;
		
		User foundUser = optionalUser.get();
		this.userRepository.delete(foundUser);
		return true;
	}

	@Override
	public List<User> search(String name) {
		return this.userRepository.search("%" + name + "%");
	}

	@Override
	public List<User> search(String name, Pageable pageable) {
		return this.userRepository.findByNameContaining(name, pageable);
	}

	@Override
	public Boolean signUp(User user) {
		// check exists userName
		this.userRepository.findByUserName(user.getUserName())
		.ifPresent((u) -> {
			throw new UserAlreadyExistException("User with userName: %s already existed!".formatted(u.getUserName()));
		});

		user.setDeleted(false);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		user.setRoles(this.roleRepository.findByName(SecurityRole.ROLE_USER));
		this.userRepository.save(user);
		
		return true;
	}

}
