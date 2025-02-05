package com.dailycodework.dreamshops.service.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.dreamshops.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	void  deleteAllByCartId(Long id);
	

}
