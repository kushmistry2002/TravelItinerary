package com.kush.travel_api.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kush.travel_api.exception.AuthException;
import com.kush.travel_api.payload.AddPackDto;
import com.kush.travel_api.payload.ApiResponse;
import com.kush.travel_api.payload.AuthRequest;
import com.kush.travel_api.payload.ItineraryDto;
import com.kush.travel_api.payload.ItineraryResponse;
import com.kush.travel_api.payload.PackResponse;
import com.kush.travel_api.payload.PlaceDto;
import com.kush.travel_api.payload.PlaceResponse;
import com.kush.travel_api.payload.UserDto;
import com.kush.travel_api.payload.UserResponse;
import com.kush.travel_api.security.JwtProvider;
import com.kush.travel_api.services.impl.ItineraryServiceImpl;
import com.kush.travel_api.services.impl.PlaceServiceImpl;
import com.kush.travel_api.services.impl.UserServiceImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/app-auth")
public class AuthController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
	private ItineraryServiceImpl itineraryService;
    @Autowired
   	private PlaceServiceImpl placeService;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
        // Get user details
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());

        if(passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())){
            // Generate token
            return jwtProvider.generateToken(authRequest.getUsername());
        }

        throw new AuthException("Password does not match with database!..");
    }

    @GetMapping("/logout")
    public ApiResponse logout() {
    	SecurityContextHolder.clearContext();
    	return new ApiResponse("User Logout Successfully", true);
    }
    
    //Post - create users
    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) throws Exception {
    	UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }
    
    //put - update users
    @PutMapping("/updateUser")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) throws Exception {
    	UserDto user= userService.updateUser(userDto);
    	return new ResponseEntity<>(user,HttpStatus.OK);
    } 
    
    //get - All users
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllUser")
	public ResponseEntity<UserResponse> getAllUsers(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "Id",required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
			){
		return ResponseEntity.ok(this.userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir));
	}
    
    //get - By Id User Get
    @PreAuthorize("hasAuthority('ADMIN')")
  	@GetMapping("/getUser/{userId}")
  	public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId){
  		return ResponseEntity.ok(this.userService.getUserById(userId));
  	}
  	
  	//delete - delete users
    @PreAuthorize("hasAuthority('ADMIN')")
  	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
	}
    
    //post - create Itinerary 
    @PostMapping("/createItinerary")
	public ResponseEntity<ItineraryDto> createItineraries(@Valid @RequestBody ItineraryDto itineraryDto) throws Exception{
    	ItineraryDto itinerary = this.itineraryService.createItineraries(itineraryDto);
		return new ResponseEntity<ItineraryDto>(itinerary,HttpStatus.CREATED);
	}
	
    //put - update Itinerary
	@PutMapping("/updateItinerary/{itinerariesId}")
	public ResponseEntity<ItineraryDto> updateItineraries(@Valid @RequestBody ItineraryDto itineraryDto,@PathVariable Integer itinerariesId){	
		ItineraryDto updatedItinerary = this.itineraryService.updateItineraries(itineraryDto,itinerariesId);
		return new ResponseEntity<ItineraryDto>(updatedItinerary,HttpStatus.OK);
	}
	
	//delete - delete Itinerary
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/deleteItinerary/{itinerariesId}")
	public ResponseEntity<ApiResponse> deleteItineraries(@PathVariable Integer itinerariesId){
		this.itineraryService.deleteItineraries(itinerariesId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Itinerary Deleted Successfully",true),HttpStatus.OK);
	}
	
    //get - All Itinerary
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllItineray")
	public ResponseEntity<ItineraryResponse> getAllItinerary(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "itineraryid",required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
	){
		return ResponseEntity.ok(this.itineraryService.getAllItineraries(pageNumber,pageSize,sortBy,sortDir));
    }
    
    //get - By Id Itinerary Get
    @PreAuthorize("hasAuthority('ADMIN')")
  	@GetMapping("/getItineray/{itinerariesId}")
  	public ResponseEntity<ItineraryDto> getItinerayById(@PathVariable Integer itinerariesId){
  		return ResponseEntity.ok(this.itineraryService.getItinerariesById(itinerariesId));
  	}
    
    @GetMapping("/getItinerayUser")
    public ResponseEntity<List<ItineraryDto>> getItinerariesByUser(){
    	return ResponseEntity.ok(this.itineraryService.getItinerariesByUser());
    } 
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getDestination/{keyword}")
    public ResponseEntity<List<ItineraryDto>> getItinerariesByUser(@PathVariable String keyword){
    	return ResponseEntity.ok(this.itineraryService.searchItineraries(keyword));
    } 
    
    //post - create Place
    @PostMapping("/createPlace/{itineraryId}")
	public ResponseEntity<PlaceDto> createPlace(@Valid @RequestBody PlaceDto placeDto,@PathVariable Integer itineraryId){
    	System.out.println("Inside the controller");
    	PlaceDto place = this.placeService.createPlace(placeDto,itineraryId);
		return new ResponseEntity<>(place,HttpStatus.CREATED);
	}
    
    //put - update Place
    @PutMapping("/updatePlace/{placeId}")
	public ResponseEntity<PlaceDto> updatePlace(@Valid @RequestBody PlaceDto placeDto,@PathVariable Integer placeId){
    	PlaceDto place = this.placeService.updatePlace(placeDto,placeId);
		return new ResponseEntity<PlaceDto>(place,HttpStatus.OK);
	}
    
    //delete - delete place
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deletePlace/{placeId}")
    public ResponseEntity<ApiResponse> deletePlace(@PathVariable Integer placeId){
    	this.placeService.deletePlace(placeId);
    	return new ResponseEntity<ApiResponse>(new ApiResponse("Place Deleted Successfully",true),HttpStatus.OK);
	}
    
    //get - All place
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllPlace")
	public ResponseEntity<PlaceResponse> getAllPlace(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "Id",required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
	){
		return ResponseEntity.ok(this.placeService.getAllPlace(pageNumber,pageSize,sortBy,sortDir));
    }
    
    //get - By Id Place Get
    @PreAuthorize("hasAuthority('ADMIN')")
  	@GetMapping("/getPlace/{placeId}")
  	public ResponseEntity<PlaceDto> getPlaceById(@PathVariable Integer placeId){
  		return ResponseEntity.ok(this.placeService.getPlaceById(placeId));
  	}
    
    //get - Get place by ItineraryId
    @GetMapping("/getPlaceByItineray/{itineraryId}")
    public ResponseEntity<List<PlaceDto>> getPlaceByItineraries(@PathVariable Integer itineraryId){
    	return ResponseEntity.ok(this.placeService.getPlaceByItineraries(itineraryId));
    } 
    
    //get - Get All Package made by admin
    @GetMapping("/getAllPackage")
	public ResponseEntity<PackResponse> getAllPack(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "Id",required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
	){
		return ResponseEntity.ok(this.placeService.getAllPack(pageNumber,pageSize,sortBy,sortDir));
    }
    
    //Post - Add Package to userid 
    @PostMapping("/createItineraryByPackage/{packId}")
   	public ResponseEntity<ItineraryDto> createPackUser(@Valid @RequestBody AddPackDto addPackDto,@PathVariable Integer packId){
       	ItineraryDto itinerary = this.placeService.createPackUser(addPackDto,packId);
   		return new ResponseEntity<ItineraryDto>(itinerary,HttpStatus.CREATED);
   	}
}
