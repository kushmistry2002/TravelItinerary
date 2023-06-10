package com.kush.travel_api.entities;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//
@Entity
@Table(name = "itinerary")
@NoArgsConstructor
@Getter
@Setter
public class Itineraries {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer itineraryid;
	
	@ManyToOne
	private User user;
	
	@Column(name = "destination",length = 255,nullable = false)
	private String destination;
	
	@Column(columnDefinition = "DATETIME",name = "start_date",nullable = false)
	private Date start_date;
	
	@Column(columnDefinition = "DATETIME",name = "end_date",nullable = false)
	private Date end_date;
	
	@Column(columnDefinition = "TEXT",name = "activities")
	private String activities;
}
