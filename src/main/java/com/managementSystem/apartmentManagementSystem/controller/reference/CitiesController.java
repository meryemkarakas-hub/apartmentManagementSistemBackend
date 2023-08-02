package com.managementSystem.apartmentManagementSystem.controller.reference;

import com.managementSystem.apartmentManagementSystem.dto.reference.CitiesDTO;
import com.managementSystem.apartmentManagementSystem.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CitiesController {

    private final UserService userService;

    @GetMapping(value = "/cities")
    public ResponseEntity<List<CitiesDTO>> getAllCitiesList() {
        List<CitiesDTO> citiesDtoList = userService.getAllCitiesList();
        return new ResponseEntity<>(citiesDtoList, HttpStatus.OK);
    }
}
