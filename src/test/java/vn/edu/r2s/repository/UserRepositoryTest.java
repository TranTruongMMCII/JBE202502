package vn.edu.r2s.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.edu.r2s.model.User;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	private static final String USER_NAME = "user-test";
	private static final User EXPECTED_USER = User.builder()
			.age(10)
			.name("display test")
			.userName(USER_NAME)
			.email("email-test")
			.password("password-test")
			.build();
	
	@BeforeAll
	void setUp() {
		this.userRepository.save(EXPECTED_USER);
	}
	
	@AfterAll
	void tearDown() {
		User foundUser = this.userRepository.findByUserName(USER_NAME).get();
		this.userRepository.delete(foundUser);
	}
	
	@Test
	void testFindByUserName() {
		// given

		// when
		Optional<User> optUser = this.userRepository.findByUserName(USER_NAME);

		// then
		assertTrue(optUser.isPresent());
		User givenUser = optUser.get();
		assertEquals(USER_NAME, givenUser.getUserName());
		assertEquals(10, givenUser.getAge());
		assertEquals("email-test", givenUser.getEmail());
		
		// neu so luong assert qua nhieu, co the thay bang
//		assertThat(givenUser).usingRecursiveComparison().isEqualTo(EXPECTED_USER);
	}
	
	@Test
	void testFindByUserNameNotFound() {
		// given

		// when
		Optional<User> optUser = this.userRepository.findByUserName(USER_NAME + "111");

		// then
		assertTrue(optUser.isEmpty());
	}
}
