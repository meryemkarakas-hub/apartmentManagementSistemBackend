package com.managementSystem.apartmentManagementSystem.service.user;

import com.managementSystem.apartmentManagementSystem.core.dto.GeneralMessageDTO;
import com.managementSystem.apartmentManagementSystem.dto.reference.CitiesDTO;
import com.managementSystem.apartmentManagementSystem.dto.user.*;
import com.managementSystem.apartmentManagementSystem.entity.user.Cities;

import java.util.List;


public interface UserService {
	GeneralMessageDTO signUp(SignUpDTO signUpDTO);

	GeneralMessageDTO activation(ActivationDTO activationDTO);

    GeneralMessageDTO login(LoginDTO loginDTO);

	GeneralMessageDTO forgotPasswordActivation(ForgotPasswordActivationDTO forgotPasswordActivationDTO);

	List<StatisticsDTO> getStatistics();

    List<CitiesDTO> getAllCitiesList();

	GeneralMessageDTO saveOrUpdateProfile(ProfileDTO profileDTO);
}
