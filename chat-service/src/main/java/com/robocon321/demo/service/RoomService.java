package com.robocon321.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.robocon321.demo.dto.request.CreateRoomRequest;
import com.robocon321.demo.dto.response.RoomResponse;
import com.robocon321.demo.dto.response.UserResponse;
import com.robocon321.demo.model.Room;
import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.RoomRepository;
import com.robocon321.demo.repository.UserRepository;

@Service
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	public RoomResponse save(CreateRoomRequest request) {
		// map and save to db
		Room room = new Room();
		BeanUtils.copyProperties(request, room);
		for (String memberRequestId : request.getMembers()) {
			Optional<User> memberOpt = userRepository.findById(memberRequestId);
			if (memberOpt.isPresent()) {
				User member = memberOpt.get();
				room.getMembers().add(member);
			}
		}
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User createBy = userDetails.getUser();
		room.getMembers().add(createBy);

		Room newRoom = roomRepository.save(room);
		userRepository.save(createBy);

		RoomResponse response = documentToDTO(newRoom);
		return response;
	}

	public Page<RoomResponse> getPage(Pageable pageable, Predicate predicate) {
		Page<Room> page = roomRepository.findAll(predicate, pageable);
		return pageDocumentToDTO(page);
	}

	private Page<RoomResponse> pageDocumentToDTO(Page<Room> page) {
		return page.map(room -> documentToDTO(room));
	}

	private RoomResponse documentToDTO(Room room) {
		// map to room response
		RoomResponse response = new RoomResponse();
		BeanUtils.copyProperties(room, response);
		UserResponse createByResponse = new UserResponse();

		// map createBy to response
		BeanUtils.copyProperties(room.getCreateBy(), createByResponse);
		response.setCreateBy(createByResponse);

		// map to member response
		List<UserResponse> membersResponse = new ArrayList<>();
		for (User member : room.getMembers()) {
			UserResponse memberResponse = new UserResponse();
			BeanUtils.copyProperties(member, memberResponse);
			membersResponse.add(memberResponse);
		}
		response.setMembers(membersResponse);

		return response;
	}
	
	public List<RoomResponse> findRoomJoined() {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		List<Room> rooms = roomRepository.findByMembers(user);
		List<RoomResponse> response = rooms.stream().map(room -> documentToDTO(room)).toList();
		return response;
	}

}
