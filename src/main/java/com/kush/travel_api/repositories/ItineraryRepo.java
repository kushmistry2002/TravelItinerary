package com.kush.travel_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kush.travel_api.entities.Itinerary;
import com.kush.travel_api.entities.UserInfo;

public interface ItineraryRepo extends JpaRepository<Itinerary, Integer> {
	List<Itinerary> findByUser(UserInfo user);
	
//	@Query(value = "select * from itinerary where destination like ?%1%",nativeQuery = true)
	List<Itinerary> findBydestinationIgnoreCaseContaining(String keyword);
	
//	@Query(value = "select * from itinerary where itineraryid=?1",nativeQuery = true)
//	List<Itinerary> findByitineraryidContaining(List<Integer> id);
}
