package com.managementSystem.apartmentManagementSystem.dto.user;

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
