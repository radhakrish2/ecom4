package com.dailycodework.dreamshops.service.cart;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailycodework.dreamshops.entity.Cart;
import com.dailycodework.dreamshops.entity.CartItem;
import com.dailycodework.dreamshops.entity.Product;
import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.service.product.IProductService;

@Service
public class CartItemService implements ICartItemService{
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private IProductService productService;
	@Autowired
	private ICartService cartService;
	@Autowired
	private CartRepository cartRepository;

	@Override
	public void addItemToCart(Long cartId, Long productId, int quantity) {
		Cart cart = cartService.getCart(cartId);
		Product product = productService.getProduct(productId);
		CartItem cartItem  =  cart.getCartItems()
				.stream()
				.filter(item -> item.getProduct().getId().equals(product))
				.findFirst().orElse(new CartItem());
		
	
		
		
		if(cartItem.getId() == null) {
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setUnitPrice(product.getPrice());
		}
		else {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		}
		cartItem.setTotalPrice();
		cart.addItem(cartItem);
		
		
		cartItemRepository.save(cartItem);
		
	
		
		
	}

	@Override
	public void removeItemFromCart(Long cartId, Long productId) {
		Cart cart = cartService.getCart(cartId);
		Product product = productService.getProduct(productId);
		CartItem itemToRemove = getCartItem(cartId, productId);
		cart.removeItem(itemToRemove);
		cartRepository.save(cart);
		
	}

	@Override
	public void updateItemQuantity(Long cartId, Long productId, int quantity) {
		Cart cart = cartService.getCart(cartId);
		cart.getCartItems().stream()
		.filter(item  -> item.getProduct().getId().equals(productId))
		.findFirst().ifPresent(item ->{
			item.setQuantity(quantity);
			item.setUnitPrice(item.getProduct().getPrice());
			item.setTotalPrice();
		});
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		cartRepository.save(cart);
		
	}
	@Override
	public CartItem getCartItem(Long cartId, Long productId) {
		Cart cart =  cartService.getCart(cartId);
		return  cart.getCartItems().stream()
				.filter(item  -> item.getProduct().getId().equals(productId))
				.findFirst().orElseThrow(()-> new ResourceNotFoundException("Item  not found"));
	}

}
