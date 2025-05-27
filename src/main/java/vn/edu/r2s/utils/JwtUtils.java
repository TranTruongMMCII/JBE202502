package vn.edu.r2s.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import vn.edu.r2s.model.Role;
import vn.edu.r2s.security.CustomUserDetails;

@Component
public class JwtUtils {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.duration}")
	private long jwtDuration;

	public String generateToken(final CustomUserDetails user) {
		Map<String, Object> claims = new HashMap<>();
		var expirationMillis = new Date(System.currentTimeMillis() + 1000 * jwtDuration); // 7 days

		return Jwts.builder()
				.claims(claims)
				.subject(user.getUsername())
				.claim("roles", user.getRoles().stream().map(Role::getName).toList())
				.issuedAt(new Date())
				.expiration(expirationMillis)
				.signWith(this.getSignKey())
				.compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.setSigningKey(this.getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
}
