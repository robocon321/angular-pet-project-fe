package com.robocon321.demo.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRoomResponse {
	private String id;
	private String name;
	private UserResponse createBy;
	private List<UserResponse> members;
}
