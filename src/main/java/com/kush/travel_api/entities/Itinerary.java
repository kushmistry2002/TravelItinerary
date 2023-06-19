package com.kush.travel_api.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itinerary")
@Getter
@Setter
@NoArgsConstructor
public class Itinerary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer itineraryid;
	
	@ManyToOne
	private UserInfo user;
	
	@Column(name = "destination",length = 255,nullable = false)
	private String destination;
	
	@Column(columnDefinition = "DATE",name = "start_date",nullable = false)
	private LocalDate start_date;
	
	@Column(columnDefinition = "DATE",name = "end_date",nullable = false)
	private LocalDate end_date;
	
	@Column(columnDefinition = "TEXT",name = "activities")
	private String activities;
	
	@Column(columnDefinition = "BOOLEAN",name = "shared")
	private String shared;
	
	@Column(columnDefinition = "varchar",name = "sharedName",length = 50,nullable = false)
	private String sharedName;
	
	@OneToMany(mappedBy = "itinerary",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Place> places = new ArrayList<>();
	
	@OneToMany(mappedBy = "shareitinerary",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<ShareItinerary> shareItinerary = new ArrayList<>();
}
