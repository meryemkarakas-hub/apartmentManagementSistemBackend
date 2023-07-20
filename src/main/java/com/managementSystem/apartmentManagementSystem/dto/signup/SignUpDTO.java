package com.managementSystem.apartmentManagementSystem.dto.signup;

import java.time.LocalDate;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpDTO {
	@NotEmpty(message = "The username field cannot be empty.")
    private String username;
	
    @NotEmpty(message = "The e-mail address field cannot be empty.")
    @Email
    private String email;
    
    @NotEmpty(message = "The re-email address field cannot be empty.")
    @Email
    private String reEmail;
    
   
    private LocalDate birthdate;
    
}
