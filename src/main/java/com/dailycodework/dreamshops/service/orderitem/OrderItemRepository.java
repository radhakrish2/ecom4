package com.dailycodework.dreamshops.service.orderitem;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.dreamshops.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
