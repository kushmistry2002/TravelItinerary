package com.kush.travel_api.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
	private Integer Id;
	
	@NotEmpty(message = "Name is invaild or Null!...")
	private String name;
	
	@NotEmpty(message = "Name is invaild or Null!...")
	private String address;
	
	private ItineraryDto itinerary;
}
