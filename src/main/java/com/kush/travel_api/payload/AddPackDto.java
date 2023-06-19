package com.kush.travel_api.payload;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddPackDto {
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate start_date;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate end_date;
	
	@NotEmpty(message = "Shared is invaild or Null!...")
	private String shared;
	
	@NotEmpty(message = "Shared Name is invaild or Null!...")
	private String sharedName;
}
