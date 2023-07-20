package com.managementSystem.apartmentManagementSystem.dto.signup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActivationDTO {
    private String password;
    private String rePassword;
    private String activationCode;
}
