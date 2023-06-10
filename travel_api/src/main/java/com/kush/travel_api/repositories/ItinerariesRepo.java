package com.kush.travel_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kush.travel_api.entities.Itineraries;
import com.kush.travel_api.entities.User;

public interface ItinerariesRepo extends JpaRepository<Itineraries, Integer> {
	List<Itineraries> findByUser(User user);
}
