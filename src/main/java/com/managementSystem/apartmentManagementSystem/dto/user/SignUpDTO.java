package com.managementSystem.apartmentManagementSystem.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class SignUpDTO {
    private String username;
    private String email;
    private String reEmail;
    private LocalDate birthdate;
    
}
