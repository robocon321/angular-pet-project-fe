package com.robocon321.demo.service.impl;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robocon321.demo.exception.ExpiredRefreshTokenException;
import com.robocon321.demo.exception.NotFoundRefreshToken;
import com.robocon321.demo.model.RefreshToken;
import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.UserRepository;
import com.robocon321.demo.service.RefreshTokenService;
import com.robocon321.demo.util.JwtUtils;

@Service	
public class RefreshTokenServiceImpl implements RefreshTokenService {	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	public RefreshToken createRefreshToken(User user) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setExpireDate(LocalDate.now().plusDays(1));
		refreshToken.setToken(UUID.randomUUID().toString());
		user.setRefreshToken(refreshToken);
		user = userRepository.save(user);
		return user.getRefreshToken();
	}
	
	public void verifyExpiration(User user) {
		RefreshToken refreshToken = user.getRefreshToken();
		if(refreshToken.getExpireDate().compareTo(LocalDate.now()) < 0) {
			user.setRefreshToken(null);
			userRepository.save(user);
			throw new ExpiredRefreshTokenException(refreshToken.getToken(), "Refresh token was expired. Please make a new signin request");
		}
	}

	@Override
	public String refreshAccessToken(String request) {
		Optional<User> userOpt = userRepository.findByRefreshTokenToken(request);
		if(userOpt.isEmpty()) throw new NotFoundRefreshToken("Token [" + request + "] invalid");
		User user = userOpt.get();
		verifyExpiration(user);
		String newToken = jwtUtils.generateTokenFromJwtToken(user.getEmail());
		return newToken;
	}
}
