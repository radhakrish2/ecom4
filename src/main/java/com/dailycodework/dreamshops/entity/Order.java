package com.dailycodework.dreamshops.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.dailycodework.dreamshops.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long Id;
	    private LocalDate orderDate;
	    private BigDecimal totalAmount;
	    @Enumerated(EnumType.STRING)
	    private OrderStatus orderStatus;
	    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	    private Set<OrderItem> orderItems = new HashSet<>();

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User users;

}

