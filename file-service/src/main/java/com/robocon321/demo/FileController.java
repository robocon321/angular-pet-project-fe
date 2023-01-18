package com.robocon321.demo;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//Annotation
@RestController
@RequestMapping("api/file")
public class FileController {
	public final String PATH_RESOURCE = "src/main/resources/";
	
	// Uploading a file
	@PostMapping
	public String uploadFile(@RequestParam MultipartFile file, @RequestParam String uploadDir, @RequestParam String fileName) throws Exception{
		// Setting up the path of the file
		String fileUploadStatus;
		
		// Try block to check exceptions
		try(InputStream inputStream = file.getInputStream()) {
			saveMultipart(file, PATH_RESOURCE + uploadDir, fileName);
			fileUploadStatus = "File Uploaded Successfully";
			
		}
	
		// Catch block to handle exceptions
		catch (Exception e) {
			throw e;
		}
		return fileUploadStatus;
	}
	
	@GetMapping
	public String sayHi() {
		return "Hello world";
	}
	
	@DeleteMapping
	public boolean deleteFile(@RequestBody String path) {
		File file = new File(PATH_RESOURCE + path);
		return file.delete();
	}

	private void saveMultipart(MultipartFile multipart, String path, String fileName) throws IOException {
		if (!multipart.isEmpty()) {			
			try {
				saveFile(path, fileName, multipart);
			} catch (IOException e) {
				throw e;
			}	
		}		
	}
	
	private void saveFile(String uploadDir, String fileName, 
			MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName +"."+ getExtension(multipartFile));
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException ex) {
			throw ex;
		}
	}
	
	private String getExtension(MultipartFile multipart) {
		String[] multipartArr = multipart.getOriginalFilename().split("\\.");
		if(multipartArr.length == 0) throw new RuntimeException("File invalid");
		return multipartArr[multipartArr.length - 1];
	}
}
