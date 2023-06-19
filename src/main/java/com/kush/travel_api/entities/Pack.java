package com.kush.travel_api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pack")
@Getter
@Setter
@NoArgsConstructor
public class Pack {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@Column(name = "destination",length = 255,nullable = false)
	private String destination;
	
	@Column(columnDefinition = "TEXT",name = "activities")
	private String activities;
}
