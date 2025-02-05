package com.dailycodework.dreamshops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.cart.ICartItemService;
import com.dailycodework.dreamshops.service.cart.ICartService;

@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
	@Autowired
	private ICartItemService cartItemService;
	@Autowired
	private ICartService cartservice;
	
	@PostMapping("/additemToCart")
	
	public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,@RequestParam long productId,@RequestParam int quantity){
		
		
		System.err.println(cartId + " fsdfsdgsdgsdgsg");
		try {
			if(cartId == null) {				
				cartId = cartservice.initializeNewCart();
			}
			
			
			cartItemService.addItemToCart(cartId, productId, quantity);
			
			
			return ResponseEntity.ok(new ApiResponse("Add Item to cart", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		 catch (ObjectOptimisticLockingFailureException e) {			    
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
			}
		
	}
	@DeleteMapping("/delete/cart/{cartId}/item/{itemId}")
	public ResponseEntity<ApiResponse> removeItem(@PathVariable long cartId,@PathVariable  long itemId){
		try {
			cartItemService.removeItemFromCart(cartId, itemId);
			return ResponseEntity.ok(new ApiResponse("Removed from Cart", null));
		} catch (ResourceNotFoundException e) {		
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	@PutMapping("/update/cart/{cartId}/item/{itemId}")
	public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable long cartId,@PathVariable long itemId,@RequestParam int quantity){
		try {
			cartItemService.updateItemQuantity(cartId, itemId, quantity);
			return ResponseEntity.ok(new ApiResponse("Update Item Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

		}
	}
	
}
