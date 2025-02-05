package com.dailycodework.dreamshops.service.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.dreamshops.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	  List<Order> findByUsersId(Long userId);

}
