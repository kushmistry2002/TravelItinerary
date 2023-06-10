package com.kush.travel_api.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kush.travel_api.entities.Itineraries;
import com.kush.travel_api.entities.User;
import com.kush.travel_api.exception.ResourceNotFoundException;
import com.kush.travel_api.payload.itinerariesDto;
import com.kush.travel_api.repositories.ItinerariesRepo;
import com.kush.travel_api.repositories.UserRepo;
import com.kush.travel_api.services.ItinerariesService;

@Service
public class ItinerariesServiceImpl implements ItinerariesService {

	@Autowired
	private ItinerariesRepo itinerariesRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public itinerariesDto createItineraries(itinerariesDto itinerariesDto,Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
		Itineraries itineraries= this.modelMapper.map(itinerariesDto,Itineraries.class);
		itineraries.setUser(user);
		Itineraries savedItineraries = this.itinerariesRepo.save(itineraries); 
		return this.modelMapper.map(savedItineraries, itinerariesDto.class);
	}

	@Override
	public itinerariesDto updateItineraries(itinerariesDto itinerariesDto, Integer itinerariesId,Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
		Itineraries itId = this.itinerariesRepo.findById(itinerariesId).orElseThrow(()->new ResourceNotFoundException("Itineraries", "Itineraries Id", itinerariesId));
		Itineraries itineraries = this.modelMapper.map(itinerariesDto, Itineraries.class);
		itineraries.setUser(user);
		itineraries.setDestination(itinerariesDto.getDestination());
		itineraries.setStart_date(itinerariesDto.getStart_date());
		itineraries.setEnd_date(itinerariesDto.getEnd_date());
		itineraries.setActivities(itinerariesDto.getActivities());
		Itineraries updatedItineraries = this.itinerariesRepo.save(itineraries);
		return this.modelMapper.map(updatedItineraries, itinerariesDto.class);
	}

	@Override
	public void deleteItineraries(Integer itinerariesId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Itineraries> getAllItineraries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Itineraries getItinerariesById(Integer itinerariesId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Itineraries> getItinerariesByUser(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Itineraries> searchItineraries(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
