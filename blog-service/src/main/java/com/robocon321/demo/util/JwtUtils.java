package com.robocon321.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${com.robocon321.demo.jwtSecret}")
	private String jwtSecret;

	@Value("${com.robocon321.demo.jwtExpirationMs}")
	private int jwtExpirationsMs;
	
	public String getEmailIdFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}

	public boolean validateJwtToken(String token) throws RuntimeException {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			logger.error("SignatureException: " + e.getMessage());
			throw new SignatureException("Invalid JWT signature: " + e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("MalformedJwtException: " + e.getMessage());
			throw new MalformedJwtException("Invalid JWT token: " + e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("ExpiredJwtException: " + e.getMessage());
			throw new ExpiredJwtException(null, null, "JWT token is expired: " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("UnsupportedJwtException: " + e.getMessage());
			throw new UnsupportedJwtException("JWT token is unsupported: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("IllegalArgumentException: " + e.getMessage());
			throw new IllegalArgumentException("JWT claims string is empty: " + e.getMessage());
		}
	}
}
