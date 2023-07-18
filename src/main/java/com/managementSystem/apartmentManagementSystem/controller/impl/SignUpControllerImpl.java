package com.managementSystem.apartmentManagementSystem.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.managementSystem.apartmentManagementSystem.controller.SignUpController;
import com.managementSystem.apartmentManagementSystem.dto.GeneralMessageDTO;
import com.managementSystem.apartmentManagementSystem.dto.SignUpDTO;
import com.managementSystem.apartmentManagementSystem.service.SignUpService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SignUpControllerImpl implements SignUpController{

	@Autowired
	private final SignUpService signUpService;

	@Override
	public ResponseEntity<?> register(@Valid SignUpDTO signUpDTO) {
		GeneralMessageDTO result = signUpService.signUp(signUpDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	

}
