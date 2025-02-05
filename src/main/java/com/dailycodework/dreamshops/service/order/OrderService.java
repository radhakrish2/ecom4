package com.dailycodework.dreamshops.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailycodework.dreamshops.dto.OrderDto;
import com.dailycodework.dreamshops.entity.Cart;
import com.dailycodework.dreamshops.entity.Order;
import com.dailycodework.dreamshops.entity.OrderItem;
import com.dailycodework.dreamshops.entity.Product;
import com.dailycodework.dreamshops.enums.OrderStatus;
import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.service.cart.CartService;
import com.dailycodework.dreamshops.service.product.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService implements IOrderService {
	@Autowired
	private OrderRepository  orderRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CartService cartService;
	
	@Transactional   
	@Override
	public Order placeOrder(Long userId) {
		Cart cart = cartService.getCartByUserId(userId);
		Order order = createOrder(cart);
		List<OrderItem> orderItemList = createOrderItems(order, cart);
		order.setOrderItems(new HashSet<>(orderItemList));
		order.setTotalAmount(calculateTotalAmount(orderItemList));
		Order savedOrder=orderRepository.save(order);
		cartService.clearCart(cart.getId());
		return savedOrder;
	}
	
	private Order createOrder(Cart cart) {
		Order order = new Order();
		order.setUsers(cart.getUser());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderDate(LocalDate.now());
		return order;
    }
	
	private List<OrderItem> createOrderItems(Order order, Cart cart){
		return cart.getCartItems().stream().map(cartItem -> {
			Product product  = cartItem.getProduct();
			product.setInventory(product.getInventory()-cartItem.getQuantity());
			productRepository.save(product);
			return new OrderItem(
					order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
		}).toList();
	}
	
	 private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
	        return  orderItemList
	                .stream()
	                .map(item -> item.getPrice()
	                        .multiply(new BigDecimal(item.getQuantity())))
	                .reduce(BigDecimal.ZERO, BigDecimal::add);
	     }
	
	
	
	@Override
	public OrderDto getOrder(Long orderId) {
		
		return orderRepository.findById(orderId)
				.map(this :: convertToDto)
				.orElseThrow(()->new ResourceNotFoundException("Order not found"));
	}
	@Override
	public List<OrderDto> getUserOrders(Long userId) {
		 List<Order> orders = orderRepository.findByUsersId(userId);
	     return  orders.stream().map(this :: convertToDto).toList();
	}
	
	private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

}
