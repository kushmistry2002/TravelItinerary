package com.kush.travel_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kush.travel_api.entities.Itineraries;
import com.kush.travel_api.payload.itinerariesDto;
import com.kush.travel_api.repositories.ItinerariesRepo;
import com.kush.travel_api.services.ItinerariesService;

@RestController
@RequestMapping("/")
public class ItineraryController {
	
	@Autowired
	private ItinerariesService itinerariesService;
	
	//create
	@PostMapping("/user/{userId}/itinerary")
	public ResponseEntity<itinerariesDto> createItineraries(@RequestBody itinerariesDto itinerariesDto,@PathVariable Integer userId){
		itinerariesDto itinerary = this.itinerariesService.createItineraries(itinerariesDto, userId);
		return new ResponseEntity<itinerariesDto>(itinerary,HttpStatus.CREATED);
	}
	
	@PutMapping("/user/{userId}/itinerary/{itineraryId}")
	public ResponseEntity<itinerariesDto> updateItineraries(@RequestBody itinerariesDto itinerariesDto,@PathVariable Integer itineraryId,@PathVariable Integer userId){	
		itinerariesDto updatedItinerary = this.itinerariesService.updateItineraries(itinerariesDto,itineraryId,userId);
		return new ResponseEntity<itinerariesDto>(updatedItinerary,HttpStatus.OK);
	}
}
