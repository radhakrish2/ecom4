package com.dailycodework.dreamshops.dto;

import java.util.List;

import com.dailycodework.dreamshops.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	private Long id;
	private String name;
	private String brand;
	private Double price;
	private int inventory;  //quantity
	private String description;
	private Category category;
	private List<ImageDto> Images;

}
