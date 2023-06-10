package com.kush.travel_api.services;

import java.util.List;

import com.kush.travel_api.entities.Itineraries;
import com.kush.travel_api.payload.itinerariesDto;

public interface ItinerariesService {
	//create
	itinerariesDto createItineraries(itinerariesDto itinerariesDto,Integer userId);
	
	//update
	itinerariesDto updateItineraries(itinerariesDto itinerariesDto,Integer itinerariesId,Integer userId);
	
	//delete
	void deleteItineraries(Integer itinerariesId);
	
	//get all itineraries
	List<Itineraries> getAllItineraries();
	
	//get by id
	Itineraries getItinerariesById(Integer itinerariesId);
	
	//get itineraries by userid
	List<Itineraries> getItinerariesByUser(Integer userId);
	
	//search itineraries
	List<Itineraries> searchItineraries(String keyword);
}
