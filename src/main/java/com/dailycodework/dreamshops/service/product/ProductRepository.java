package com.dailycodework.dreamshops.service.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.dreamshops.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByName(String name);

	List<Product> findByBrand(String brand);

	List<Product> findByCategoryNameAndBrand(String category,String brand);

	List<Product> findByBrandAndName(String brand, String name);

	List<Product> findByCategoryName(String category);

	Long countByBrandAndName(String brand, String name);
	

}
