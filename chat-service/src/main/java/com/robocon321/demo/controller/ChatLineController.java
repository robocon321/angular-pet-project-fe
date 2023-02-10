package com.robocon321.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.robocon321.demo.dto.request.MessageRequest;
import com.robocon321.demo.dto.response.ChatLineResponse;
import com.robocon321.demo.service.ChatService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chat/chat-line")
public class ChatLineController {
	@Autowired
	private ChatService chatService;
	
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping
	public ChatLineResponse newChatLine(@RequestBody @Valid MessageRequest request) {
		ChatLineResponse chatLineResponse =  chatService.newChatLine(request);
        simpMessagingTemplate.convertAndSendToUser(chatLineResponse.getRoom().getId(),"/room/private", chatLineResponse);
		return chatLineResponse;
	}
    
    @GetMapping
    public List<ChatLineResponse> get(@RequestParam String roomId) {
    	return chatService.get(roomId);
    }
}
