package com.dailycodework.dreamshops.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.entity.Product;
import com.dailycodework.dreamshops.exception.ProductNotFoundException;
import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.UpdateProductRequest;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
	private final IProductService productService;
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProducts(){
		List<Product> product=productService.getAllProducts();
		List<ProductDto> convertedProduct = productService.getConvertedProducts(product);
		return ResponseEntity.ok(new ApiResponse("success", convertedProduct));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getProductsById(@PathVariable Long id){
		try {
			Product product= productService.getProduct(id);
			ProductDto productDto =  productService.convertToDto(product);
			return ResponseEntity.ok(new ApiResponse("success",productDto));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
		try {
			Product theProduct=productService.addProduct(product);
			ProductDto productDto =  productService.convertToDto(theProduct);

			return ResponseEntity.ok(new ApiResponse("Added successfully", productDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id,@RequestBody UpdateProductRequest product){
		try {
			Product theProduct=productService.updateProduct(product, id);
			ProductDto productDto =  productService.convertToDto(theProduct);
			return ResponseEntity.ok(new ApiResponse("successfully Updated", productDto));
		} catch (ResourceNotFoundException e) { 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
		try {
			productService.deleteProduct(id);
			return ResponseEntity.ok(new ApiResponse("Deled Successfully", id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
	}
	
	@GetMapping("/brand-and-name")
	public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName,@RequestParam String productName){
		try {
			List<Product> products=productService.getProductsByBrandAndName(brandName, productName);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product found",null));
			}
			List<ProductDto> convertedProducts =  productService.getConvertedProducts(products);

			return ResponseEntity.ok(new ApiResponse("Success",convertedProducts));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body (new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/category-and-brand")
	public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@PathVariable String categoryName,@PathVariable String brandName){
		try {
			List<Product> products=productService.getProductsByCategoryAndBrand(categoryName, brandName);
			
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product found",null));
			}
			return ResponseEntity.ok(new ApiResponse("Success",products));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body (new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/by-name/{name}")
	public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
		try {
			List<Product> products=productService.getProductsByName(name);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product found",null));
			}
			return ResponseEntity.ok(new ApiResponse("Success",products));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body (new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/brand")
	public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand){
		try {
			List<Product> products=productService.getProductsByBrand(brand);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product found",null));
			}
			return ResponseEntity.ok(new ApiResponse("Success",products));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body (new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/by-category/{category}")
	public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category){
		try {
			List<Product> products=productService.getProductsByCategory(category);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product found",null));
			}
			return ResponseEntity.ok(new ApiResponse("Success",products));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body (new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/count/by-brand/and-name")
	public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand,@RequestParam String name){
		try {
			var productCount = productService.countProductsByBrandAndName(brand, name);
			return ResponseEntity.ok(new ApiResponse("Product Count!!",productCount));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}
	
	
	

}
