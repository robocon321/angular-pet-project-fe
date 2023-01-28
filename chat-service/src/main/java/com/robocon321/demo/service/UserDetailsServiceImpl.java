package com.robocon321.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userOpt = userRepository.findByEmail(email);
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("Not found " + email);
		}
		UserDetails userDetails = new UserDetailsImpl(userOpt.get());
		return userDetails;
	}

}
