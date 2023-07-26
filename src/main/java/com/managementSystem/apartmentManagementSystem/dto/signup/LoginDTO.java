package com.managementSystem.apartmentManagementSystem.dto.signup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {
    private String userNameOrEmail;
    private String password;
}
