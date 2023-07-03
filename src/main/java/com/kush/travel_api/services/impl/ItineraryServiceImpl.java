package com.kush.travel_api.services.impl;

import java.time.LocalDate;
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
import com.kush.travel_api.entities.ShareItinerary;
import com.kush.travel_api.entities.UserInfo;
import com.kush.travel_api.exception.AuthException;
import com.kush.travel_api.exception.ResourceNotFoundException;
import com.kush.travel_api.exception.UsernameNotFoundException;
import com.kush.travel_api.payload.ItineraryDto;
import com.kush.travel_api.payload.ItineraryResponse;
import com.kush.travel_api.repositories.ItineraryRepo;
import com.kush.travel_api.repositories.ShareItineraryRepo;
import com.kush.travel_api.repositories.UserRepo;

@Service
public class ItineraryServiceImpl {

	@Autowired
	private ItineraryRepo itineraryRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ShareItineraryRepo shareItineraryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ItineraryDto createItineraries(ItineraryDto itineraryDto) {
		Itinerary savedItineraries = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal). getUsername();
		UserInfo userInfo = userRepo.getByUserId(username);
		
		UserInfo user = this.userRepo.findById(userInfo.getId())
				.orElseThrow(()->new ResourceNotFoundException("User", "User Id", userInfo.getId()));
		
		Itinerary itineraries= this.modelMapper.map(itineraryDto,Itinerary.class);
		
		LocalDate date = itineraryDto.getStart_date();
		LocalDate date1 = itineraryDto.getEnd_date();
		String shared = itineraryDto.getShared();
		String sharedName = itineraryDto.getSharedName();
		System.out.println(sharedName);
				
		if(shared.equals("0")) {
			if(sharedName.equalsIgnoreCase("Null") || sharedName.equals("")) {
				itineraries.setUser(user);
				itineraries.setSharedName("Null");
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
			//sharedName == Username login
			if(shareduser.getUsername().equals(username)) {
				throw new AuthException("You can't use your username: "+username+" as the shared Name: "+itineraryDto.getSharedName());
			}
			if(itineraryDto.getSharedName().equals("Null")){
				throw new AuthException("Shared Name is invalid or Null!...");
			}
			else {
				itineraries.setUser(user);
				savedItineraries = this.itineraryRepo.save(itineraries);
				ShareItinerary shareItinerary = new ShareItinerary();
				shareItinerary.setName(savedItineraries.getSharedName());
				shareItinerary.setShareitinerary(itineraries);
				this.shareItineraryRepo.save(shareItinerary);
			}
		} 
		else {
			throw new AuthException("Shared is invalid or Null!...");
		}
		return this.modelMapper.map(savedItineraries, ItineraryDto.class);
	}

//	public ItineraryDto updateItineraries(ItineraryDto itineraryDto,Integer itinerariesId) {
//		Itinerary updatedItineraries = null;
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String username = ((UserDetails)principal). getUsername();
//		UserInfo userInfo = userRepo.getByUserId(username);
//		
//		UserInfo user = this.userRepo.findById(userInfo.getId())
//				.orElseThrow(()->new ResourceNotFoundException("User", "User Id", userInfo.getId()));
//		
//		Itinerary itineraries = this.itineraryRepo.findById(itinerariesId)
//				.orElseThrow(()->new ResourceNotFoundException("Itineraries", "Itineraries Id", itinerariesId));
//		
//		String sharedName = itineraryDto.getSharedName();
//		String prevShared = itineraries.getShared();
//		
//		if(user.getUserRole().equals("ADMIN"))
//		{
//			itineraries.setUser(itineraries.getUser());
//			itineraries.setDestination(itineraryDto.getDestination());
//			itineraries.setStart_date(itineraryDto.getStart_date());
//			itineraries.setEnd_date(itineraryDto.getEnd_date());
//			itineraries.setActivities(itineraryDto.getActivities());
//			itineraries.setShared(itineraryDto.getShared());
//			
//			if(itineraryDto.getShared().equals("0")) {
//				itineraries.setSharedName("Null");
//				if(itineraryDto.getShared().equals(prevShared)) {
//					
//				}
//				else {
//					ShareItinerary shareItinerary = this.shareItineraryRepo.findByitineraryid(itineraries.getItineraryid());
//					this.shareItineraryRepo.delete(shareItinerary);
//				}
//				updatedItineraries = this.itineraryRepo.save(itineraries);	
//			}
//			else {
//				itineraries.setSharedName(sharedName);
//				UserInfo shareduser = userRepo.getByUserId(sharedName);
//				if(shareduser == null) {	
//					throw new UsernameNotFoundException("User", "User Name", sharedName);
//				}
//				//sharedName == Username login
//				if(shareduser.getUsername().equals(username)) {
//					throw new AuthException("You can't use your username: "+username+" as the shared Name: "+itineraryDto.getSharedName());
//				}
//				if(itineraryDto.getSharedName().equals("Null")){
//					throw new AuthException("Shared Name is invalid or Null!...");
//				}
//				else {
//					if(itineraryDto.getShared().equals(prevShared)) {
//						
//					}
//					else {						
//						updatedItineraries = this.itineraryRepo.save(itineraries);
//						ShareItinerary shareItinerary = this.shareItineraryRepo.findByitineraryid(itineraries.getItineraryid());
//						shareItinerary.setName(updatedItineraries.getSharedName());
//						shareItinerary.setShareitinerary(itineraries);
//						this.shareItineraryRepo.save(shareItinerary);
//					}
//				}
//			}
//			return this.modelMapper.map(updatedItineraries, ItineraryDto.class);
//		}
//		else {
//			if(user.getId().equals(itineraries.getUser().getId())) {
//				itineraries.setUser(user);
//				itineraries.setDestination(itineraryDto.getDestination());
//				itineraries.setStart_date(itineraryDto.getStart_date());
//				itineraries.setEnd_date(itineraryDto.getEnd_date());
//				itineraries.setActivities(itineraryDto.getActivities());
//				itineraries.setShared(itineraries.getShared());
//				
//				if(itineraries.getShared().equals("0")) {
//					itineraries.setSharedName("Null");
//					if(itineraryDto.getShared().equals(prevShared)) {
//						
//					}
//					else {
//						ShareItinerary shareItinerary = this.shareItineraryRepo.findByitineraryid(itineraries.getItineraryid());
//						this.shareItineraryRepo.delete(shareItinerary);
//					}
//					updatedItineraries = this.itineraryRepo.save(itineraries);	
//				}
//				else {
//					itineraries.setSharedName(itineraryDto.getSharedName());
//					UserInfo shareduser = userRepo.getByUserId(sharedName);
//					//shredName Check
//					if(shareduser == null) {	
//						throw new UsernameNotFoundException("User", "User Name", sharedName);
//					}
//					//sharedName == Username login
//					if(shareduser.getUsername().equals(username)) {
//						throw new AuthException("You can't use your username: "+username+" as the shared Name: "+itineraryDto.getSharedName());
//					}
//					//shredName == null check
//					if(itineraryDto.getSharedName().equals("Null")){
//						throw new AuthException("Shared Name is invalid or Null!...");
//					}
//					else {
//						if(itineraryDto.getShared().equals(prevShared)) {
//							
//						}
//						else {						
//							updatedItineraries = this.itineraryRepo.save(itineraries);
//							ShareItinerary shareItinerary = this.shareItineraryRepo.findByitineraryid(itineraries.getItineraryid());
//							shareItinerary.setName(updatedItineraries.getSharedName());
//							shareItinerary.setShareitinerary(itineraries);
//							this.shareItineraryRepo.save(shareItinerary);
//						}
//					}
//				}
//				return this.modelMapper.map(updatedItineraries, ItineraryDto.class);
//			}
//			else {
//				throw new AuthException("You do not access to update itinerary please contact with Admin");
//			}
//		}
//	}
	
	public ItineraryDto updateItineraries(ItineraryDto itineraryDto, Integer itinerariesId) throws AuthException {
		// TODO Auto-generated method stub
		
		Itinerary updateItineraries=null;
		//ShareItinerary shareItinerary=null;
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=((UserDetails)principal).getUsername();
		UserInfo userinfo=userRepo.getByUserId(username);
		UserInfo user=this.userRepo.findById(userinfo.getId()).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userinfo.getId()));
		
		Itinerary itineraries=this.itineraryRepo.findById(itinerariesId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userinfo.getId()));
		
	
	String sharename=itineraryDto.getSharedName();
	
	if(user.getUserRole().equals("ADMIN"))
	{
		itineraries.setUser(itineraries.getUser());
		itineraries.setDestination(itineraryDto.getDestination());
		itineraries.setActivities(itineraryDto.getActivities());
		itineraries.setStart_date(itineraryDto.getStart_date());
		itineraries.setEnd_date(itineraryDto.getEnd_date());
		itineraries.setShared(itineraryDto.getShared());
		
		if(itineraries.getShared().equals("0"))
		{
			itineraries.setSharedName("null");
			updateItineraries=this.itineraryRepo.save(itineraries);
			ShareItinerary shareItinerary=this.shareItineraryRepo.findByitineraryid(itinerariesId);
			this.shareItineraryRepo.delete(shareItinerary);
			
			
		}
		else
		{
			
			
			if(itineraryDto.getSharedName().equals("null"))
			{
				throw new AuthException("Shared name is invalid..");
			}
			else
			{
				
				if(itineraries.getSharedName().equals("null"))
				{
					itineraries.setSharedName(itineraryDto.getSharedName());
					UserInfo shareUser=userRepo.getByUserId(sharename);
					updateItineraries=this.itineraryRepo.save(itineraries);
					ShareItinerary shareItinerary=new ShareItinerary();
					shareItinerary.setName(updateItineraries.getSharedName());
					shareItinerary.setShareitinerary(itineraries);
					this.shareItineraryRepo.save(shareItinerary);
				}
				else
				{
					itineraries.setSharedName(itineraryDto.getSharedName());
					UserInfo shareUser=userRepo.getByUserId(sharename);
					updateItineraries=this.itineraryRepo.save(itineraries);
					ShareItinerary shareItinerary=this.shareItineraryRepo.findByitineraryid(itineraries.getItineraryid());					
					shareItinerary.setName(itineraries.getSharedName());
					shareItinerary.setShareitinerary(itineraries);
					this.shareItineraryRepo.save(shareItinerary);
				}		
			}
					
		}
		return this.modelMapper.map(updateItineraries, ItineraryDto.class);
	}
	else
	{	
		if(user.getId()==itineraries.getUser().getId())
		{
			itineraries.setUser(user);
			itineraries.setDestination(itineraryDto.getDestination());
			itineraries.setActivities(itineraryDto.getActivities());
			itineraries.setStart_date(itineraryDto.getStart_date());
			itineraries.setEnd_date(itineraryDto.getEnd_date());
			itineraries.setShared(itineraryDto.getShared());
			
			if(itineraries.getShared().equals("0"))
			{
				itineraries.setSharedName("null");
				updateItineraries=this.itineraryRepo.save(itineraries);
				ShareItinerary shareItinerary=this.shareItineraryRepo.findByitineraryid(itinerariesId);
				this.shareItineraryRepo.delete(shareItinerary);
				
			}
			else
			{
				if(itineraryDto.getSharedName().equals("null"))
				{
					
					throw new AuthException("Shared name is not valid or null");
				}
				else
				{
					
					if(itineraries.getSharedName().equals("null"))
					{
						itineraries.setSharedName(itineraryDto.getSharedName());
						UserInfo shareUser=userRepo.getByUserId(sharename);
						updateItineraries=this.itineraryRepo.save(itineraries);
						ShareItinerary shareItinerary=new ShareItinerary();
						shareItinerary.setName(updateItineraries.getSharedName());
						shareItinerary.setShareitinerary(itineraries);
						this.shareItineraryRepo.save(shareItinerary);
					}
					else
					{
						itineraries.setSharedName(itineraryDto.getSharedName());
						UserInfo shareUser=userRepo.getByUserId(sharename);
						updateItineraries=this.itineraryRepo.save(itineraries);
						ShareItinerary shareItinerary=this.shareItineraryRepo.findByitineraryid(itineraries.getItineraryid());					
						shareItinerary.setName(itineraries.getSharedName());
						shareItinerary.setShareitinerary(itineraries);
						this.shareItineraryRepo.save(shareItinerary);
						
					}
				}
				
			}
			
			return this.modelMapper.map(updateItineraries, ItineraryDto.class);
		}
		else
		{
			throw new AuthException("You do not access to update itinerary please contact with Admin");
		}
			
	}	
			
}

	public void deleteItineraries(Integer itinerariesId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal). getUsername();
		UserInfo userInfo = userRepo.getByUserId(username);
		UserInfo user = this.userRepo.findById(userInfo.getId())
				.orElseThrow(()->new ResourceNotFoundException("User", "User Id", userInfo.getId()));

		Itinerary itinerary = this.itineraryRepo.findById(itinerariesId)
				.orElseThrow(()->new ResourceNotFoundException("Itinerary", "Itinerary Id", itinerariesId));
		
		if(user.getUserRole().equals("ADMIN"))
		{
			this.itineraryRepo.delete(itinerary);
		}
		else {
			if(user.getId().equals(itinerary.getUser().getId())) {
				this.itineraryRepo.delete(itinerary);
			}
		}
	}

	public ItineraryResponse getAllItineraries(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		Page<Itinerary> pageItinerary = this.itineraryRepo.findAll(p);
		List<Itinerary> allItinerary = pageItinerary.getContent();
		
		List<ItineraryDto> itinerariesDtos = allItinerary.stream().map((it)->this.modelMapper.map(it,ItineraryDto.class)).collect(Collectors.toList());
		
		ItineraryResponse itineraryResponse = new ItineraryResponse();
		itineraryResponse.setContent(itinerariesDtos);
		itineraryResponse.setPageNumber(pageItinerary.getNumber());
		itineraryResponse.setPageSize(pageItinerary.getSize());
		itineraryResponse.setTotalElements(pageItinerary.getTotalElements());
		itineraryResponse.setTotalPages(pageItinerary.getTotalPages());
		itineraryResponse.setLastpage(pageItinerary.isLast());
		return itineraryResponse;
	}

	public ItineraryDto getItinerariesById(Integer itinerariesId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal). getUsername();
		UserInfo userInfo = userRepo.getByUserId(username);
		UserInfo user = this.userRepo.findById(userInfo.getId())
				.orElseThrow(()->new ResourceNotFoundException("User", "User Id", userInfo.getId()));
		
		Itinerary itinerary = this.itineraryRepo.findById(itinerariesId)
				.orElseThrow(()->new ResourceNotFoundException("Itinerary", "Itinerary Id", itinerariesId));
		
		if(user.getUserRole().equals("ADMIN"))
		{
			return this.modelMapper.map(itinerary, ItineraryDto.class);
		}
		else {
			if(user.getId().equals(itinerary.getUser().getId())) {
				return this.modelMapper.map(itinerary, ItineraryDto.class);
			}
		}
		throw new AuthException("You do not access view of itineraries please contact to Admin");
	}

	public List<ItineraryDto> getItinerariesByUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		UserInfo user = userRepo.getByUserId(username);
		List<Itinerary> itineraries = this.itineraryRepo.findByUser(user);
		
		List<Itinerary> allitineraries = this.itineraryRepo.findAll();
		
		List<ShareItinerary> shareItinerary = this.shareItineraryRepo.findByUsername(username);
		List<Integer> shareId = shareItinerary.stream().map((it)->it.getShareitinerary().getItineraryid()).collect(Collectors.toList());
		for(Integer id:shareId) {
			itineraries.add(this.itineraryRepo.findById(id).orElse(null));
		}
		List<ItineraryDto> itinerariesDtos = itineraries.stream().map(it->this.modelMapper.map(it,ItineraryDto.class)).collect(Collectors.toList());
		return itinerariesDtos;
	}

	public List<ItineraryDto> searchItineraries(String keyword) {
		List<Itinerary> itinerary = this.itineraryRepo.findBydestinationIgnoreCaseContaining(keyword);
		List<ItineraryDto> itineraryDtos = itinerary.stream().map((it)->this.modelMapper.map(it, ItineraryDto.class)).collect(Collectors.toList());
		return itineraryDtos;
	}

}
