package com.kush.travel_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kush.travel_api.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
