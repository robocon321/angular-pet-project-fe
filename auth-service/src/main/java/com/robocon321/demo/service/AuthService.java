package com.robocon321.demo.service;

import com.robocon321.demo.dto.request.RegisterRequest;
import com.robocon321.demo.model.ERole;

public interface AuthService {
	void save(RegisterRequest registerRequest, ERole role);
}
