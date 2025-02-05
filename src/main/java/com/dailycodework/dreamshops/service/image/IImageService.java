package com.dailycodework.dreamshops.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.entity.Image;

public interface IImageService {
	
	Image getImageById(Long id);
	void deleteImageById(Long id);
	List<ImageDto>  saveImage(List<MultipartFile> file,Long productId);
	void updateImage(MultipartFile file,Long imageId);

}
