package com.dailycodework.dreamshops.controller;


import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.entity.Image;
import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.image.IImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
	@Autowired
	private IImageService imageService;
	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files,@RequestParam long productId){
		try {
			List<ImageDto> imagesDtos = imageService.saveImage(files, productId);
			return ResponseEntity.ok(new ApiResponse("Upload Successful", imagesDtos));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload Failed!", e.getMessage()));	
		}
		
	}
	
	@GetMapping("/image/download/{imageId}")
	public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException{
				Image image = imageService.getImageById(imageId);
				ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, ((int)image.getImage().length())));
				
				return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
						.header(HttpHeaders.CONTENT_DISPOSITION,"attachement; filename=\""+image.getFileName()+ "\"")
						.body(resource);
			 
		
	
		
	}
	
	@PutMapping("/image/{imageId}/update")
	public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestBody MultipartFile file){
		try {
			Image image = imageService.getImageById(imageId);
			if(image!=null) {
				imageService.updateImage(file,imageId);
				return ResponseEntity.ok(new ApiResponse("Update Success!",null));
			}
		} catch (ResourceNotFoundException e) {
			
			return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
		}
		return ResponseEntity.status(500).body(new ApiResponse("Update failed!!", HttpStatus.INTERNAL_SERVER_ERROR));
		
	}
	@DeleteMapping("/delete/{imageId}")
	public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
		try {
			Image image = imageService.getImageById(imageId);
			if(image!=null) {
				imageService.deleteImageById(imageId);
				return ResponseEntity.ok(new ApiResponse("Delete Success!",null));
			}
		} catch (ResourceNotFoundException e) {
			
			return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
		}
		return ResponseEntity.status(500).body(new ApiResponse("Delete failed!!", HttpStatus.INTERNAL_SERVER_ERROR));
		
	}
}
