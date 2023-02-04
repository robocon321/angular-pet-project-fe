package com.robocon321.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.robocon321.demo.dto.request.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MessageController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

//	@MessageMapping("/hello")
//	@SendTo("/topic/messages")
//	public String send(String username) {
//		return "Hello, " + username;
//	}
	
    @MessageMapping("/message")
    public Message sayHi(@Payload Message message) {
		log.info("Client send "+ message);
    	simpMessagingTemplate.convertAndSend("/topic/messages", message);
		return message;
	}
    
    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
		log.info("Client send "+ message);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private", message);
        return message;
    }
}
