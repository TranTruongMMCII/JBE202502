package vn.edu.r2s.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.r2s.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

}
