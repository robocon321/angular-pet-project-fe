package com.robocon321.demo.dto.response;

import java.time.LocalDate;

import com.robocon321.demo.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogResponse {
	private String id;
	private String title;
	private String image;
	private String description;
	private LocalDate createdDate;
	private String createdBy;
}
