package com.kush.travel_api.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	@NotEmpty
	@Size(min=4,message = "Username must be min of 4 characters")
	private String username;
	@NotEmpty
	@Size(min=4,max=8,message = "Password must be minimum of 3 characters and max of 8 characters!....")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
	private String password;
	@Email(message = "Email address is not valid!....")
	private String email;
}
