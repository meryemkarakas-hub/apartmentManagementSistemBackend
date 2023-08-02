package com.managementSystem.apartmentManagementSystem.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDTO {
    private String profileImage;
    private String city;
    private String phoneNumber;
}
