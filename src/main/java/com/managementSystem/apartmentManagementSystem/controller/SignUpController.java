package com.managementSystem.apartmentManagementSystem.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.managementSystem.apartmentManagementSystem.dto.SignUpDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/users",produces = MediaType.APPLICATION_JSON_VALUE)
public interface SignUpController {
	
	@RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    ResponseEntity<?> register(@Valid @RequestBody SignUpDTO registerDTO);
}
