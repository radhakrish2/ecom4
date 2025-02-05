package com.dailycodework.dreamshops.service.product;

import java.util.List;

import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.entity.Product;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.UpdateProductRequest;

public interface IProductService {
	Product addProduct(AddProductRequest product);
	List<Product> getAllProducts();
	Product getProduct(Long id);
	Product updateProduct(UpdateProductRequest productRequest,Long id);
	void deleteProduct(Long id);
	List<ProductDto> getConvertedProducts(List<Product> products);
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String category,String brand);
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String brand,String name);
	Long countProductsByBrandAndName(String brand,String name);
	ProductDto convertToDto(Product product);	
	
	
}
