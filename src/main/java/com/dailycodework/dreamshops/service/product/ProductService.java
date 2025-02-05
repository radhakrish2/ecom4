package com.dailycodework.dreamshops.service.product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.entity.Category;
import com.dailycodework.dreamshops.entity.Image;
import com.dailycodework.dreamshops.entity.Product;
import com.dailycodework.dreamshops.exception.ProductNotFoundException;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.UpdateProductRequest;
import com.dailycodework.dreamshops.service.category.CategoryRepository;
import com.dailycodework.dreamshops.service.image.ImageRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
	
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ImageRepository imageRepository;
	@Autowired
	private ModelMapper modelmapper ;

	@Override
	public Product addProduct(AddProductRequest request) {
		Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
				.orElseGet(()->{
					Category newCategory  = new Category(request.getCategory().getName());
					return categoryRepository.save(newCategory);
				});
		request.setCategory(category);
		return productRepository.save(createProduct(request, category));
	}
	
	private Product createProduct(AddProductRequest request,Category category) {
		return new Product(
				request.getName(),
				request.getBrand(),
				request.getPrice(),
				request.getInventory(),
				request.getDescription(),
				category
				);
		
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProduct(Long id) {
		
		return productRepository.findById(id)
				
				.orElseThrow(()->new ProductNotFoundException("Product Not Found"));
	}

	@Override
	public Product updateProduct(UpdateProductRequest productRequest, Long id) {
		return productRepository.findById(id)
				.map(existingProduct -> updateExistingProduct(existingProduct, productRequest))
				.map(productRepository::save)
				.orElseThrow(()-> new ProductNotFoundException("Product Not Found"));
		
	}
	
	private Product updateExistingProduct(Product existingProduct,UpdateProductRequest requestProduct) {
		existingProduct.setName(requestProduct.getName());
		existingProduct.setBrand(requestProduct.getBrand());
		existingProduct.setPrice(requestProduct.getPrice());
		existingProduct.setInventory(requestProduct.getInventory());
		existingProduct.setDescription(requestProduct.getDescription());
		Category category  = categoryRepository.findByName(requestProduct.getCategory().getName());
		existingProduct.setCategory(category);
		
		return existingProduct;
		
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.findById(id)
		.ifPresentOrElse(productRepository::delete,
				()->{throw new ProductNotFoundException("Product Not Found");});
		
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		return productRepository.findByCategoryNameAndBrand(category,brand);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		return productRepository.findByBrandAndName(brand,name);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return productRepository.countByBrandAndName(brand,name);
	}
	
	@Override
	public ProductDto convertToDto(Product product) {
		ProductDto productDto = modelmapper.map(product, ProductDto.class);
		List<Image> image = imageRepository.findByProductId(product.getId());
		List<ImageDto> imageDtos = image.stream()
				.map(images-> new ImageDto(images.getId(), images.getFileName(),images.getDownloadUrl()))
				.toList();
		productDto.setImages(imageDtos);		
		return productDto;
	}
	
	@Override
	public List<ProductDto> getConvertedProducts(List<Product> products){
		return products.stream().map(this::convertToDto).toList();
	}

}
