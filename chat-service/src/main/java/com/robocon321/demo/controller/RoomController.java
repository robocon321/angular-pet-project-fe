package com.robocon321.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;
import com.robocon321.demo.dto.request.CreateRoomRequest;
import com.robocon321.demo.dto.response.CreateRoomResponse;
import com.robocon321.demo.dto.response.UserResponse;
import com.robocon321.demo.model.Room;
import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.RoomRepository;
import com.robocon321.demo.service.RoomService;
import com.robocon321.demo.service.UserDetailsServiceImpl;
import com.robocon321.demo.util.JwtUtils;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/chat/room")
@Slf4j
public class RoomController {
	@Autowired
	private RoomService roomService;
	
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
			
	@GetMapping
	public List<CreateRoomResponse> getRoom() {
		return roomService.findRoomJoined();
	}	
	
    @PostMapping
	public void save(@Valid @RequestBody CreateRoomRequest request) {
    	log.info("Request " + request);
		CreateRoomResponse response = roomService.save(request);
		for(UserResponse member : response.getMembers()) {
	        simpMessagingTemplate.convertAndSendToUser(member.getId(),"/new-room/private", response);	
		}
	}
}
