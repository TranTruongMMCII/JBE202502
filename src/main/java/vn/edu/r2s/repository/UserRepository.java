package vn.edu.r2s.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.edu.r2s.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByName(final String name);
	
	List<User> findByNameContaining(final String name, final Pageable pageable);

	List<User> findByNameContains(final String name);

	@Query(nativeQuery = true, 
			value = "SELECT `user`.`id`, `user`.`deleted`, `user`.`email`, `user`.`display_name` FROM `jbe202502`.`user` where display_name like ?1")
	List<User> search(final String name);
	
	Optional<User> findByUserName(final String userName);
}
