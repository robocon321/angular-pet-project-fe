package com.robocon321.demo.service;

import com.robocon321.demo.model.RefreshToken;
import com.robocon321.demo.model.User;

public interface RefreshTokenService {
	RefreshToken createRefreshToken(User user);
	String refreshAccessToken(String refreshToken);
}
