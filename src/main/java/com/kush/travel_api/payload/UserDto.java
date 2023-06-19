package com.kush.travel_api.payload;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private Integer Id;
	@NotEmpty
	@Size(min=4,message = "Username must be min of 4 characters")
	private String username;
	@Email(message = "Email address is not valid!....")
	private String email;
	@NotEmpty
	@Size(min=8,max=11,message = "Password must be minimum of 8 characters and max of 11 characters!....")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,11}$")
	private String password;
	@NotEmpty
	private String userRole;
}
