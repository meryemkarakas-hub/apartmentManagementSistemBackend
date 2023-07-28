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
    private String username;
    private String email;
    private String reEmail;
    private LocalDate birthdate;
    
}
