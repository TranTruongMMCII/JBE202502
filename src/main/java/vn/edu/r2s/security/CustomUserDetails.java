package vn.edu.r2s.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.edu.r2s.model.Role;
import vn.edu.r2s.model.User;

@AllArgsConstructor
@Getter
@Setter
public class CustomUserDetails implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String userName;
	private final String password;
	private final Set<GrantedAuthority> authorities; // ROLE_ADMIN
	private final Set<Role> roles; // ADMIN
	
	public CustomUserDetails(final User user) {
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
		this.roles = user.getRoles().stream().collect(Collectors.toSet());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}
}
