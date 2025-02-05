package com.dailycodework.dreamshops.service.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.dreamshops.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);

}
