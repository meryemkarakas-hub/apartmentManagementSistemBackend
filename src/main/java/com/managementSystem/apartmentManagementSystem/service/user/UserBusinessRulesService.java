package com.managementSystem.apartmentManagementSystem.service.user;

import com.managementSystem.apartmentManagementSystem.dto.user.SignUpDTO;


public interface UserBusinessRulesService {
	void checkRules(SignUpDTO signUpDTO);
}
