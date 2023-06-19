package com.kush.travel_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kush.travel_api.entities.UserInfo;

@Repository
public interface UserRepo extends JpaRepository<UserInfo, Integer>{
	public boolean existsByUsername(String username);

	Optional<UserInfo> findByUsername(String username);

	@Query(value = "select * from user where username=?1",nativeQuery = true) 
	UserInfo getByUserId(String username);
}
