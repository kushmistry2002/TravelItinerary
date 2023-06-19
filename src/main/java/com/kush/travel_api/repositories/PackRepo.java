package com.kush.travel_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kush.travel_api.entities.Pack;

public interface PackRepo extends JpaRepository<Pack, Integer> {

}
