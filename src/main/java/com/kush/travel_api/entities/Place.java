package com.kush.travel_api.entities;

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

@Entity(name = "place")
@Table(name = "place")
@Getter
@Setter
@NoArgsConstructor
public class Place {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@ManyToOne
	private Itinerary itinerary;
	
	@Column(name = "name",length = 255,nullable = false)
	private String name;
	
	@Column(name = "address",length = 255,nullable = false)
	private String address;
}
