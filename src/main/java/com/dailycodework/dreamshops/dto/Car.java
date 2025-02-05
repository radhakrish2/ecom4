package com.dailycodework.dreamshops.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Car {
	
	@Id
	Long id;
	String name;

}
