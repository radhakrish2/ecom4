package com.dailycodework.dreamshops.service.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dailycodework.dreamshops.dto.UserDto;
import com.dailycodework.dreamshops.entity.User;
import com.dailycodework.dreamshops.exception.AlreadyExistsException;
import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.request.CreateUserRequest;
import com.dailycodework.dreamshops.request.UserUpadateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
	
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	@Override
	public User getUserById(Long userId) {	
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found!"));
	}

	@Override
	public User createUser(CreateUserRequest request) {
		
		return Optional.of(request)
				.filter(user -> !userRepository.existsByEmail(request.getEmail()))
				.map(req -> {
					User user = new User();
					user.setEmail(request.getEmail());
					user.setFirstName(request.getFirstName());
					user.setLastName(request.getLastName());
					user.setPassword(request.getPassword());
					return userRepository.save(user);			
				}).orElseThrow(()-> new AlreadyExistsException("Oops! "+ request.getEmail()+ "already Exists!"));
	}

	@Override
	public User updateUser(UserUpadateRequest request, Long userId) {
		
		 return  userRepository.findById(userId).map(existingUser ->{
	            existingUser.setFirstName(request.getFirstName());
	            existingUser.setLastName(request.getLastName());
	            return userRepository.save(existingUser);
	        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
	}

	@Override
	public void deleteUser(Long userId) {
		 userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () ->{
	            throw new ResourceNotFoundException("User not found!");
	        });
		
	}

	 @Override
	    
	 public UserDto convertUserToDto(User user) {
	       
		 return modelMapper.map(user, UserDto.class);
	    
	 }

}
