package com.dailycodework.dreamshops.service.cart;

import java.math.BigDecimal;

import com.dailycodework.dreamshops.entity.Cart;

public interface ICartService {
	Cart getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);
	Long initializeNewCart();
	Cart getCartByUserId(Long userId);
	

}
