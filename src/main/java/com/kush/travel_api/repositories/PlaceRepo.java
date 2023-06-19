package com.kush.travel_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kush.travel_api.entities.Itinerary;
import com.kush.travel_api.entities.Place;
import com.kush.travel_api.payload.PlaceDto;

public interface PlaceRepo extends JpaRepository<Place, Integer> {
	@Query(value = "select * from place where itinerary_itineraryid=?1",nativeQuery = true)
	List<Place> findByItinerary(Integer itinerariesId);
}
