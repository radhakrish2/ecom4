package com.dailycodework.dreamshops.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

	private Long id;
	private String fileName;
	private String downloadUrl;
	
}
