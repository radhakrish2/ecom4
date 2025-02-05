package com.dailycodework.dreamshops.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("test")
public class Test {

	
	@GetMapping
	public String getMethodName() {
		return  "App is running";
	}
	
}
