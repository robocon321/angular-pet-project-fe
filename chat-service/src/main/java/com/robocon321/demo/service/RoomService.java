package com.robocon321.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.robocon321.demo.dto.request.CreateRoomRequest;
import com.robocon321.demo.dto.response.CreateRoomResponse;
import com.robocon321.demo.dto.response.UserResponse;
import com.robocon321.demo.model.Person;
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

	public CreateRoomResponse save(CreateRoomRequest request) {
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

		CreateRoomResponse response = documentToDTO(newRoom);
		return response;
	}

	public Page<CreateRoomResponse> getPage(Pageable pageable, Predicate predicate) {
		Page<Room> page = roomRepository.findAll(predicate, pageable);
		return pageDocumentToDTO(page);
	}

	private Page<CreateRoomResponse> pageDocumentToDTO(Page<Room> page) {
		return page.map(room -> documentToDTO(room));
	}

	private CreateRoomResponse documentToDTO(Room room) {
		// map to room response
		CreateRoomResponse response = new CreateRoomResponse();
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
	
	public List<CreateRoomResponse> findRoomJoined() {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		List<Room> rooms = roomRepository.findByMembers(user);
		List<CreateRoomResponse> response = rooms.stream().map(room -> documentToDTO(room)).toList();
		return response;
	}

	public List<Person> findPeople() {
		LookupOperation lookup = LookupOperation.newLookup().from("address").localField("addresses").foreignField("_id")
				.as("addresses");

		MatchOperation match = Aggregation.match(Criteria.where("addresses.street").regex(".*" + ".*"));

		ProjectionOperation project = Aggregation.project().andInclude("id", "name", "addresses");

		Aggregation aggregation = Aggregation.newAggregation(lookup, match, project);

		AggregationResults<Person> result = mongoTemplate.aggregate(aggregation, "person", Person.class);
		return result.getMappedResults();
	}

}
