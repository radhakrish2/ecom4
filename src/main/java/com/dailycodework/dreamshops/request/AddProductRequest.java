package com.dailycodework.dreamshops.request;

import java.math.BigDecimal;

import com.dailycodework.dreamshops.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {
	private Long id;
	private String name;
	private String brand;
	private BigDecimal price;
	private int inventory;  //quantity
	private String description;
	private Category category;
}
