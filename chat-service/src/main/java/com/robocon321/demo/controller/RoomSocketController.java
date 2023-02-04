package com.robocon321.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.robocon321.demo.dto.request.CreateRoomRequest;
import com.robocon321.demo.dto.response.CreateRoomResponse;
import com.robocon321.demo.dto.response.UserResponse;
import com.robocon321.demo.service.RoomService;
import com.robocon321.demo.service.UserDetailsServiceImpl;
import com.robocon321.demo.util.JwtUtils;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RoomSocketController {
	@Autowired
	private RoomService roomService;

	@Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

//    @MessageMapping("/create-room")
//	public void save(@Valid @RequestBody CreateRoomRequest request) {
//    	log.info("Request " + request);
//    	validateToken(request.getToken());
////		CreateRoomResponse response = roomService.save(request);		
////		for(UserResponse member : response.getMembers()) {
////	        simpMessagingTemplate.convertAndSendToUser(member.getId(),"/private", response);		
////		}
//	}

	private void validateToken(String jwt) {
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			String email = jwtUtils.getEmailIdFromJWT(jwt);
			UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}

}
