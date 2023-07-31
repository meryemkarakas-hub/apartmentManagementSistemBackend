package com.managementSystem.apartmentManagementSystem.dto.user;

import java.time.LocalDate;


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
