package com.dailycodework.dreamshops.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.dreamshops.entity.Cart;
import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.cart.ICartService;

@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
	@Autowired
	private ICartService cartService;
	@GetMapping("/{cartId}")
	public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
		try {
			Cart cart  =  cartService.getCart(cartId);
			return ResponseEntity.ok(new ApiResponse("Id Found", cart));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	
	}
	@DeleteMapping("/delete/{cartId}")
	public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
		try {
			cartService.clearCart(cartId);
			return ResponseEntity.ok(new ApiResponse("Clear Cart Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()+cartId , null));
		}
		
	}
	
	@GetMapping("/totalAmount/{cartId}")
	public ResponseEntity<ApiResponse>  getTotalAmount(@PathVariable Long cartId){
		try {
			BigDecimal totalPrice=cartService.getTotalPrice(cartId);
			return ResponseEntity.ok(new ApiResponse("Total Price : ", totalPrice));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()+cartId , null));
		}
		
	}
	
}
