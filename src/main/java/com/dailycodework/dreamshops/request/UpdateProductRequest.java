package com.dailycodework.dreamshops.request;

import java.math.BigDecimal;

import com.dailycodework.dreamshops.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
	private Long id;
	private String name;
	private String brand;
	private BigDecimal price;
	private int inventory;  //quantity
	private String description;
	private Category category;

}
