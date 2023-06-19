package com.kush.travel_api.payload;

import java.util.List;

import com.kush.travel_api.entities.Itinerary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItineraryResponse {
	private List<ItineraryDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean lastpage;
}
