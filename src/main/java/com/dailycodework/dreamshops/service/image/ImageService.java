package com.dailycodework.dreamshops.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.entity.Image;
import com.dailycodework.dreamshops.entity.Product;
import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.service.product.IProductService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
	
	private  final ImageRepository imageRepository;
	
	private final IProductService productService;

	@Override
	public Image getImageById(Long id) {
		
		return imageRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No image found with id "+id));
	}

	@Override
	public void deleteImageById(Long id) {
		imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
				()->{throw new ResourceNotFoundException("Image not found,Please enter correct id!!");});
		
	}

	@Override
	public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
		Product product = productService.getProduct(productId);
		List<ImageDto> imageDtos = new ArrayList<>();
		for(MultipartFile f : files) {
			try {
				Image image = new Image();
				image.setFileName(f.getOriginalFilename());
				image.setFileType(f.getContentType());
				image.setImage(new SerialBlob(f.getBytes()));
				image.setProduct(product);
				String buildDownloadedUrl = "/api/v1/images/image/download/";
				String downloadUrl = buildDownloadedUrl+image.getId()+" /" ;	
				image.setDownloadUrl(downloadUrl);
				Image savedImage  = imageRepository.save(image);
				savedImage.setDownloadUrl(buildDownloadedUrl+savedImage.getId());
				imageRepository.save(savedImage);
				ImageDto imageDto =  new ImageDto();
				imageDto.setId(savedImage.getId());
				imageDto.setFileName(savedImage.getFileName());
				imageDto.setDownloadUrl(savedImage.getDownloadUrl());
				imageDtos.add(imageDto);
				
			}catch(IOException |SQLException e) {
				throw new RuntimeException(e.getMessage());
				
			}
		}
		return imageDtos;
	}

	@Override
	public void updateImage(MultipartFile file, Long imageId) {
		Image image = getImageById(imageId);
		try {
			image.setFileName(file.getOriginalFilename());
			image.setFileType(file.getContentType());
			image.setImage(new SerialBlob(file.getBytes()));
			imageRepository.save(image);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());	
			
		}
	}

}
