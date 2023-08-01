package com.managementSystem.apartmentManagementSystem.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDTO {
    private MultipartFile profileImage;
    private String city;
    private String phoneNumber;
}
