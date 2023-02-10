package com.robocon321.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robocon321.demo.dto.request.MessageRequest;
import com.robocon321.demo.dto.response.ChatLineResponse;
import com.robocon321.demo.dto.response.MessageResponse;
import com.robocon321.demo.dto.response.RoomResponse;
import com.robocon321.demo.dto.response.UserResponse;
import com.robocon321.demo.exception.BadRequestException;
import com.robocon321.demo.model.ChatLine;
import com.robocon321.demo.model.Message;
import com.robocon321.demo.model.Room;
import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.ChatLineRepository;
import com.robocon321.demo.repository.MessageRepository;
import com.robocon321.demo.repository.RoomRepository;

@Service
public class ChatService {
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private ChatLineRepository chatLineRepository;
	
	@Autowired
	private MessageRepository messageRepository;
	
	public ChatLineResponse newChatLine(MessageRequest request) {
		Message message = new Message();
		ChatLine chatLine = new ChatLine();
	
		Optional<Room> roomOpt = roomRepository.findById(request.getRoomId());
		if(roomOpt.isEmpty()) throw new BadRequestException("Not found room: " + request.getRoomId());
		Room room = roomOpt.get();
		chatLine.setRoom(room);
	
		Optional<ChatLine> replyToOpt = chatLineRepository.findById(request.getReplyToId());
		ChatLine replyTo =replyToOpt.isEmpty() ? null : replyToOpt.get();
		chatLine.setReplyTo(replyTo);

		message.setText(request.getContent());
		Message newMessage = messageRepository.save(message);
		chatLine.setMessage(newMessage);
		
		ChatLine newChatLine = chatLineRepository.save(chatLine);
		ChatLineResponse response = documentToDTO(newChatLine);	
		
		return response;
	}
	
	private ChatLineResponse documentToDTO(ChatLine chatLine) {
		ChatLineResponse chatLineResponse = new ChatLineResponse();
		BeanUtils.copyProperties(chatLine, chatLineResponse);		

		Message newMessage = chatLine.getMessage();
		MessageResponse messageResponse = new MessageResponse();
		BeanUtils.copyProperties(newMessage, messageResponse);
		chatLineResponse.setMessage(messageResponse);
		
		User createBy = chatLine.getCreateBy();
		UserResponse createByResponse = new UserResponse();
		BeanUtils.copyProperties(createBy, createByResponse);
		chatLineResponse.setCreateBy(createByResponse);
		
		RoomResponse roomResponse = new RoomResponse();
		BeanUtils.copyProperties(chatLine.getRoom(), roomResponse);
		chatLineResponse.setRoom(roomResponse);
		
		ChatLine replyTo = chatLine.getReplyTo();
		if(replyTo != null) {
			ChatLineResponse replyToResponse = new ChatLineResponse();
			BeanUtils.copyProperties(replyTo, replyToResponse);
			chatLineResponse.setReplyTo(replyToResponse);
		}
		
		return chatLineResponse;
	}

	public List<ChatLineResponse> get(String roomId) {
		Optional<Room> roomOpt = roomRepository.findById(roomId);
		if(roomOpt.isEmpty()) return null;
		List<ChatLine> chatLines = chatLineRepository.findByRoom(roomOpt.get());
		return chatLines.stream().map(item -> documentToDTO(item)).toList();
	}
}
