package com.managementSystem.apartmentManagementSystem.service.signup;

import com.managementSystem.apartmentManagementSystem.dto.signup.SignUpDTO;


public interface SignUpBusinessRulesService {
	void checkRules(SignUpDTO signUpDTO);
}
