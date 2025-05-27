package vn.edu.r2s.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.r2s.model.Profile;
import vn.edu.r2s.repository.ProfileRepository;
import vn.edu.r2s.response.SuccessResponse;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = "/profiles")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileRepository profileRepository;

	@GetMapping(path = "")
	public SuccessResponse<List<Profile>> getAll() {
		return SuccessResponse.of(this.profileRepository.findAll());
	}

}
