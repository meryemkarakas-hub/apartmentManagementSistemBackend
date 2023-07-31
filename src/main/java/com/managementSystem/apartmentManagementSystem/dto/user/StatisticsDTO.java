package com.managementSystem.apartmentManagementSystem.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class StatisticsDTO {
    private String username;
    private String email;
    private LocalDateTime registrationTime;
    private LocalDateTime activationTime;
    private LocalDateTime lastLoginTime;
}
