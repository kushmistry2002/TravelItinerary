package com.kush.travel_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kush.travel_api.payload.UserDto;
@Service
public interface UserService {
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto,Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
}
