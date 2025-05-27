package vn.edu.r2s.controller;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.r2s.dto.request.SignInRequest;
import vn.edu.r2s.dto.request.SignUpRequest;
import vn.edu.r2s.dto.response.SignInResponse;
import vn.edu.r2s.mapper.UserMapper;
import vn.edu.r2s.model.User;
import vn.edu.r2s.response.SuccessResponse;
import vn.edu.r2s.security.CustomUserDetails;
import vn.edu.r2s.service.UserService;
import vn.edu.r2s.utils.JwtUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	@PostMapping(path = "/signUp")
	public SuccessResponse<Boolean> signUp(@RequestBody SignUpRequest request) {
		User user = UserMapper.INSTANCE.toModel(request);

		return SuccessResponse.of(this.userService.signUp(user));
	}
	
	@PostMapping(path = "/signIn")
	public SuccessResponse<SignInResponse> signIn(@RequestBody SignInRequest request) {
		Authentication authentication = this.authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

        // Retrieve user details from the authenticated token
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Generate JWT token
        String accessToken = this.jwtUtils.generateToken(userDetails);
        Date expriedDate = this.jwtUtils.extractExpiration(accessToken);

        return SuccessResponse.of(SignInResponse.builder()
        		.token(accessToken)
        		.expiredDate(expriedDate)
        		.build());
	}
}
