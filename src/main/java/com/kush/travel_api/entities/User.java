package com.kush.travel_api.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="user_name",length = 50,nullable = false)
	private String username;
	@Column(name="password",length = 20,nullable = false)
	private String password;
	@Column(name="email",length = 50,nullable = false)
	private String email;
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Itineraries> itineraries = new ArrayList<>();
}
