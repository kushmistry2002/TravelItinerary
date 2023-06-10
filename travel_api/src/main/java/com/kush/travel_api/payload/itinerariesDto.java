package com.kush.travel_api.payload;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class itinerariesDto {
	private UserDto user;
	private String destination;
	private Date start_date;
	private Date end_date;
	private String activities;
}
