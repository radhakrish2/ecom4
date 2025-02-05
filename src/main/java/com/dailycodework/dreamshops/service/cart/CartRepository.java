package com.dailycodework.dreamshops.service.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.dreamshops.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUserId(Long userId);

}
