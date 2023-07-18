package com.managementSystem.apartmentManagementSystem.service;

import com.managementSystem.apartmentManagementSystem.dto.GeneralMessageDTO;
import com.managementSystem.apartmentManagementSystem.dto.SignUpDTO;


public interface SignUpService {
	GeneralMessageDTO signUp(SignUpDTO signUpDTO);
}
