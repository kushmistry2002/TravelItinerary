package com.kush.travel_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kush.travel_api.entities.ShareItinerary;

public interface ShareItineraryRepo extends JpaRepository<ShareItinerary, Integer> {
	@Query(value = "select * from shareitinerary where name=?1",nativeQuery = true)
	List<ShareItinerary> findByUsername(String username);

	@Query(value = "select * from shareitinerary where shareitinerary_itineraryid=?1",nativeQuery = true)
	ShareItinerary findByitineraryid(Integer itineraryid);

}
