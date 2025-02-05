package com.dailycodework.dreamshops.service.user;

import com.dailycodework.dreamshops.dto.UserDto;
import com.dailycodework.dreamshops.entity.User;
import com.dailycodework.dreamshops.request.CreateUserRequest;
import com.dailycodework.dreamshops.request.UserUpadateRequest;

public interface IUserService {
	
	User getUserById(Long userId);

	User createUser(CreateUserRequest request);

	User updateUser(UserUpadateRequest request, Long userId);

	void deleteUser(Long userId);

	UserDto convertUserToDto(User user);

}
