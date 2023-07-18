package com.managementSystem.apartmentManagementSystem.service.impl;

import org.springframework.stereotype.Service;
import com.managementSystem.apartmentManagementSystem.dto.GeneralMessageDTO;
import com.managementSystem.apartmentManagementSystem.dto.SignUpDTO;
import com.managementSystem.apartmentManagementSystem.entity.SignUp;

import com.managementSystem.apartmentManagementSystem.mapper.SignUpMapper;
import com.managementSystem.apartmentManagementSystem.repository.SignUpRepository;
import com.managementSystem.apartmentManagementSystem.service.SignUpService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SignUpServiceImpl implements SignUpService {
	
	
    private final SignUpMapper signUpMapper;
    private final SignUpRepository signUpRepository;


    @Override
   public GeneralMessageDTO signUp(SignUpDTO signUpDTO) {
        SignUp signUp = signUpMapper.toEntity(signUpDTO); 
        signUpRepository.save(signUp); 
         return new GeneralMessageDTO(1, "İşleminiz başarıyla gerçekleştirildi.",null); 
    }
}
