package com.managementSystem.apartmentManagementSystem.service.signup;

import com.managementSystem.apartmentManagementSystem.core.dto.GeneralMessageDTO;
import com.managementSystem.apartmentManagementSystem.dto.signup.ActivationDTO;
import com.managementSystem.apartmentManagementSystem.dto.signup.ForgotPasswordActivationDTO;
import com.managementSystem.apartmentManagementSystem.dto.signup.LoginDTO;
import com.managementSystem.apartmentManagementSystem.dto.signup.SignUpDTO;


public interface SignUpService {
	GeneralMessageDTO signUp(SignUpDTO signUpDTO);

	GeneralMessageDTO activation(ActivationDTO activationDTO);

    GeneralMessageDTO login(LoginDTO loginDTO);

	GeneralMessageDTO forgotPasswordActivation(ForgotPasswordActivationDTO forgotPasswordActivationDTO);
}
