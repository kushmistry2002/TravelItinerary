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
public class PackDto {
	private Integer Id;
	
	@NotEmpty(message = "Destination is invaild or Null!...")
	private String destination;
	
	@NotEmpty(message = "Activities is invaild or Null!...")
	private String activities;
}
