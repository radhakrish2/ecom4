package com.dailycodework.dreamshops.dto;


import com.dailycodework.dreamshops.entity.Product;

public interface ProductMapper {
	
	ProductDto toProductDto(Product product);

}
