package com.robocon321.demo.service.impl;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.robocon321.demo.exception.TokenRefreshException;
import com.robocon321.demo.model.RefreshToken;
import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.RefreshTokenRepository;
import com.robocon321.demo.repository.UserRepository;
import com.robocon321.demo.service.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public RefreshToken createRefreshToken(User user) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setExpireDate(LocalDate.now().plusDays(1));
		refreshToken.setToken(UUID.randomUUID().toString());
		user.setRefreshToken(refreshToken);
		user = userRepository.save(user);
		return user.getRefreshToken();
	}
	
	public RefreshToken verifyExpiration(RefreshToken refreshToken) {
		if(refreshToken.getExpireDate().compareTo(LocalDate.now()) < 0) {
			refreshTokenRepository.delete(refreshToken);
			throw new TokenRefreshException(refreshToken.getToken(), "Refresh token was expired. Please make a new signin request");
		}
		return refreshToken;
	}
}
