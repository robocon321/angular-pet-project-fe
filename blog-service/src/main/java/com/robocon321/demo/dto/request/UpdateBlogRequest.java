package com.robocon321.demo.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBlogRequest {
	@NotBlank(message = "Id must not be blank")
	private String id;
	@NotBlank(message = "Title must not be blank")
	private String title;
	private MultipartFile image;
	@NotBlank(message = "Description must not be blank")
	private String description;
}
