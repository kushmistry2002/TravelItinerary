package com.kush.travel_api.payload;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ItineraryDto {
	private Integer itineraryid;
	
	@NotEmpty(message = "Destination is invaild or Null!...")
	private String destination;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate start_date;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate end_date;
	
	@NotEmpty(message = "Activities is invaild or Null!...")
	private String activities;
	
	@NotEmpty(message = "Shared is invaild or Null!...")
	private String shared;
	
	@NotEmpty(message = "Shared Name is invaild or Null!...")
	private String sharedName;
	
	private UserDto user;
}
