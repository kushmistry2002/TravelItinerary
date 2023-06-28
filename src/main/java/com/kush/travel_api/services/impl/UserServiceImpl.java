package com.kush.travel_api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kush.travel_api.entities.Itinerary;
import com.kush.travel_api.entities.UserInfo;
import com.kush.travel_api.exception.AuthException;
import com.kush.travel_api.exception.ResourceNotFoundException;
import com.kush.travel_api.payload.ItineraryResponse;
import com.kush.travel_api.payload.UserDto;
import com.kush.travel_api.payload.UserResponse;
import com.kush.travel_api.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserDetailsService {

	@Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ModelMapper modelMapper;
    
	@Override
	public UserDetails loadUserByUsername(String username){
		UserInfo user = userRepo.findByUsername(username)
                .orElseThrow(()->new com.kush.travel_api.exception.UsernameNotFoundException("User", "User Name", username));
		
        var authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole()));

        return new User(user.getUsername(), user.getPassword(), authorities);
	}
	
	public UserDto createUser(UserDto userDto) throws Exception {
        // Extract parameters from request
        String username = userDto.getUsername();
        String email = userDto.getEmail();
        email = email.toLowerCase();
        String password = userDto.getPassword();
        String userRole = userDto.getUserRole();
        userRole = userRole.toUpperCase();
        
        // Check whether username exists or not
        Optional<UserInfo> isExists = userRepo.findByUsername(username);

        if (!isExists.isEmpty()) {
            throw new AuthException("User already exists.");
        }

        // Create new user
        UserInfo user = new UserInfo();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setUserRole(userRole);

        // Save user
        if(userRole.equals("ADMIN") || userRole.equals("USER")) {
	        UserInfo savedUser = this.userRepo.save(user);
	        return this.modelMapper.map(savedUser, UserDto.class); 
        }
        else {
        	throw new AuthException("userRole Not match with Database.");
        }
    }
	
	public UserDto getById() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal). getUsername();
		return this.modelMapper.map(userRepo.getByUserId(username),UserDto.class);
	}

	public UserDto updateUser(UserDto userDto) throws Exception {
		UserInfo savedUser = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		UserInfo user = userRepo.getByUserId(username);
		
		String email = userDto.getEmail();
		email = email.toLowerCase();
		String userRole = userDto.getUserRole();
        userRole = userRole.toUpperCase();
        
		if(user.getUserRole().equals("ADMIN")) {
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			if(userRole.equals("ADMIN") || userRole.equals("USER")) {
				user.setUserRole(userDto.getUserRole());
		        savedUser = this.userRepo.save(user);
	        }
			return this.modelMapper.map(savedUser, UserDto.class); 
		}
		else {
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			user.setUserRole(user.getUserRole());
			UserInfo updatedUser = this.userRepo.save(user);
			return this.modelMapper.map(updatedUser, UserDto.class);
		}
	}

	public UserResponse getAllUsers(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {

		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		Page<UserInfo> pageUser = this.userRepo.findAll(p);
		List<UserInfo> allUser = pageUser.getContent();
		
		List<UserInfo> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
		
		UserResponse userResponse = new UserResponse();
		userResponse.setContent(userDtos);
		userResponse.setPageNumber(pageUser.getNumber());
		userResponse.setPageSize(pageUser.getSize());
		userResponse.setTotalElements(pageUser.getTotalElements());
		userResponse.setTotalPages(pageUser.getTotalPages());
		userResponse.setLastpage(pageUser.isLast());
		return userResponse;
	}

	public UserDto getUserById(Integer userId) {
		UserInfo user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}
	
	public void deleteUser(Integer userId) {
		UserInfo user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
	}
}
