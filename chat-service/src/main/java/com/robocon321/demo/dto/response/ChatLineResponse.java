package com.robocon321.demo.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatLineResponse {
	private String id;
	private UserResponse createBy;
	private LocalDate createDate;
	private RoomResponse room;
	private ChatLineResponse replyTo;
	private MessageResponse message;
}
