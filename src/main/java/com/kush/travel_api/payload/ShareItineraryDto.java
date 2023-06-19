package com.kush.travel_api.payload;

import com.kush.travel_api.entities.Itinerary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShareItineraryDto {
	private Integer Id;
	private String name;
	private Itinerary shareitinerary;
}
