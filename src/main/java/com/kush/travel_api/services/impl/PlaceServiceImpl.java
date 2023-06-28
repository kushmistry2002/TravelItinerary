package com.kush.travel_api.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kush.travel_api.entities.Itinerary;
import com.kush.travel_api.entities.Pack;
import com.kush.travel_api.entities.Place;
import com.kush.travel_api.entities.ShareItinerary;
import com.kush.travel_api.entities.UserInfo;
import com.kush.travel_api.exception.AuthException;
import com.kush.travel_api.exception.ResourceNotFoundException;
import com.kush.travel_api.exception.UsernameNotFoundException;
import com.kush.travel_api.payload.AddPackDto;
import com.kush.travel_api.payload.ItineraryDto;
import com.kush.travel_api.payload.PackDto;
import com.kush.travel_api.payload.PackResponse;
import com.kush.travel_api.payload.PlaceDto;
import com.kush.travel_api.payload.PlaceResponse;
import com.kush.travel_api.repositories.ItineraryRepo;
import com.kush.travel_api.repositories.PackRepo;
import com.kush.travel_api.repositories.PlaceRepo;
import com.kush.travel_api.repositories.ShareItineraryRepo;
import com.kush.travel_api.repositories.UserRepo;

@Service
public class PlaceServiceImpl {
	@Autowired
	private ItineraryRepo itineraryRepo;
	
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PlaceRepo placeRepo;
	
	@Autowired
	private PackRepo packRepo;
	
	@Autowired
	private ShareItineraryRepo shareItineraryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	//create
	public PlaceDto createPlace(PlaceDto placeDto,Integer itineraryId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal). getUsername();
		UserInfo userInfo = userRepo.getByUserId(username);
		UserInfo user = this.userRepo.findById(userInfo.getId())
				.orElseThrow(()->new ResourceNotFoundException("User", "User Id", userInfo.getId()));
		
		Itinerary itineraries = this.itineraryRepo.findById(itineraryId)
				.orElseThrow(()->new ResourceNotFoundException("Itineraries", "Itineraries Id", itineraryId));
		
		if(itineraries.getUser().getId().equals(user.getId())) {
			Place places = this.modelMapper.map(placeDto, Place.class);
			places.setItinerary(itineraries);
			
			Place savedPlace = this.placeRepo.save(places); 
			return this.modelMapper.map(savedPlace, PlaceDto.class);
		}
		else {
			throw new AuthException("Please enter your Itineraries Id");
		}
	}
	//update
	public PlaceDto updatePlace(PlaceDto placeDto,Integer placeId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal). getUsername();
		UserInfo userInfo = userRepo.getByUserId(username);
		UserInfo user = this.userRepo.findById(userInfo.getId())
				.orElseThrow(()->new ResourceNotFoundException("User", "User Id", userInfo.getId()));
		
		Place places = this.placeRepo.findById(placeId)
				.orElseThrow(()->new ResourceNotFoundException("Place", "Place Id", placeId));
		
		Itinerary itinerary = places.getItinerary();
		
		if(itinerary.getUser().getId().equals(user.getId())) {
			places.setItinerary(itinerary);
			places.setName(placeDto.getName());
			places.setAddress(placeDto.getAddress());
			Place savedPlace = this.placeRepo.save(places); 
			return this.modelMapper.map(savedPlace, PlaceDto.class);
		}
		else {
			throw new AuthException("Please enter your Place Id beacuse this "+placeId+" do not created by "+user.getUsername());
		}
	}
	//delete
	public void deletePlace(Integer placeId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal). getUsername();
		UserInfo userInfo = userRepo.getByUserId(username);
		UserInfo user = this.userRepo.findById(userInfo.getId())
				.orElseThrow(()->new ResourceNotFoundException("User", "User Id", userInfo.getId()));
		
		Place places = this.placeRepo.findById(placeId)
				.orElseThrow(()->new ResourceNotFoundException("Place", "Place Id", placeId));
		Itinerary itinerary = places.getItinerary();
		
		if(user.getUserRole().equals("ADMIN"))
		{
			this.placeRepo.delete(places);
		}
		else {
			if(user.getId().equals(itinerary.getUser().getId())) {
				this.placeRepo.delete(places);
			}
		}
	}
	//get all
	public PlaceResponse getAllPlace(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		Page<Place> pagePlace = this.placeRepo.findAll(p);
		List<Place> allPlace = pagePlace.getContent();
		
		List<PlaceDto> placeDtos = allPlace.stream().map((place)->this.modelMapper.map(place,PlaceDto.class)).collect(Collectors.toList());
		
		PlaceResponse placeResponse = new PlaceResponse();
		placeResponse.setContent(placeDtos);
		placeResponse.setPageNumber(pagePlace.getNumber());
		placeResponse.setPageSize(pagePlace.getSize());
		placeResponse.setTotalElements(pagePlace.getTotalElements());
		placeResponse.setTotalPages(pagePlace.getTotalPages());
		placeResponse.setLastpage(pagePlace.isLast());
		return placeResponse;
	}
	//get by Id
	public PlaceDto getPlaceById(Integer placeId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal). getUsername();
		UserInfo userInfo = userRepo.getByUserId(username);
		UserInfo user = this.userRepo.findById(userInfo.getId())
				.orElseThrow(()->new ResourceNotFoundException("User", "User Id", userInfo.getId()));
		
		Place places = this.placeRepo.findById(placeId)
				.orElseThrow(()->new ResourceNotFoundException("Place", "Place Id", placeId));
		Itinerary itinerary = places.getItinerary();
		
		if(user.getUserRole().equals("ADMIN"))
		{
			return this.modelMapper.map(places, PlaceDto.class);
		}
		else {
			if(user.getId().equals(itinerary.getUser().getId())) {
				return this.modelMapper.map(places, PlaceDto.class);
			}
		}
		throw new AuthException("You do not access view of Place please contact to Admin");
	}
	//get by ItineraryId
	public List<PlaceDto> getPlaceByItineraries(Integer ItinerariesId) {
		List<Place> place = this.placeRepo.findByItinerary(ItinerariesId);
		List<PlaceDto> placeDtos = place.stream().map((p)->this.modelMapper.map(p,PlaceDto.class)).collect(Collectors.toList());
		return placeDtos;
	}
	//get all packages
	public PackResponse getAllPack(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		Page<Pack> pagePack = this.packRepo.findAll(p);
		List<Pack> allPack = pagePack.getContent();
		
		List<PackDto> packDtos = allPack.stream().map((pack)->this.modelMapper.map(pack,PackDto.class)).collect(Collectors.toList());
		
		PackResponse packResponse = new PackResponse();
		packResponse.setContent(packDtos);
		packResponse.setPageNumber(pagePack.getNumber());
		packResponse.setPageSize(pagePack.getSize());
		packResponse.setTotalElements(pagePack.getTotalElements());
		packResponse.setTotalPages(pagePack.getTotalPages());
		packResponse.setLastpage(pagePack.isLast());
		return packResponse;
	}
	
	//add packages to itinerary
	public ItineraryDto createPackUser(AddPackDto addPackDto,Integer packId) {
		Itinerary savedItineraries = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal). getUsername();
		UserInfo userInfo = userRepo.getByUserId(username);
		
		UserInfo user = this.userRepo.findById(userInfo.getId())
				.orElseThrow(()->new ResourceNotFoundException("User", "User Id", userInfo.getId()));
		
		Pack pack = this.packRepo.findById(packId)
				.orElseThrow(()->new ResourceNotFoundException("Package", "Package Id", packId));
		
		Itinerary itineraries = new Itinerary();
		String shared = addPackDto.getShared();
		String sharedName = addPackDto.getSharedName();
		
		itineraries.setUser(user);
		itineraries.setDestination(pack.getDestination());
		itineraries.setActivities(pack.getActivities());
		itineraries.setEnd_date(addPackDto.getEnd_date());
		itineraries.setStart_date(addPackDto.getStart_date());
		itineraries.setShared(addPackDto.getShared());
		itineraries.setSharedName(addPackDto.getSharedName());
		
		if(shared.equals("0")) {
			if(sharedName.equals("Null") || sharedName.equals("")) {
				savedItineraries = this.itineraryRepo.save(itineraries);
			}
			else {
				throw new AuthException("Shared Name is invalid or Null!...");
			}
		}
		else if (shared.equals("1")) {		
			UserInfo shareduser = userRepo.getByUserId(sharedName);
			if(shareduser == null) {	
				throw new UsernameNotFoundException("User", "User Name", sharedName);
			}
			savedItineraries = this.itineraryRepo.save(itineraries);
			if(savedItineraries.getSharedName().equals("Null")){
				throw new AuthException("Shared Name is invalid or Null!...");
			}
			else {
				ShareItinerary shareItinerary = new ShareItinerary();
				shareItinerary.setName(savedItineraries.getSharedName());
				shareItinerary.setShareitinerary(itineraries);
				this.shareItineraryRepo.save(shareItinerary);
			}
		} 
		else {
			throw new AuthException("Shared is invalid or Null!...");
		}
		
//		Itinerary savedItineraries = this.itineraryRepo.save(itineraries); 
		String[] activities = pack.getActivities().split(",");
		if(savedItineraries != null) {			
			for(String activity:activities) {
				Place place = new Place();
				place.setItinerary(savedItineraries);
				place.setName(activity);
				place.setAddress(pack.getDestination());
				this.placeRepo.save(place);
			}
		}
		return this.modelMapper.map(savedItineraries, ItineraryDto.class);
	}
}
