package com.managementSystem.apartmentManagementSystem.service.signup.impl;

import com.managementSystem.apartmentManagementSystem.core.exception.BusinessRuleException;
import com.managementSystem.apartmentManagementSystem.core.helper.DateTimeHelper;
import com.managementSystem.apartmentManagementSystem.dto.signup.SignUpDTO;
import com.managementSystem.apartmentManagementSystem.entity.signup.SignUp;
import com.managementSystem.apartmentManagementSystem.repository.signup.SignUpRepository;
import com.managementSystem.apartmentManagementSystem.service.signup.SignUpBusinessRulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SignUpBusinessRulesServiceImpl implements SignUpBusinessRulesService {
    private final SignUpRepository signUpRepository;

    @Override
    public void checkRules(SignUpDTO signUpDTO) {
        Optional<SignUp> userFindByUsername = signUpRepository.findByUsername(signUpDTO.getUsername());
        if (userFindByUsername.isPresent()) {
            throw new BusinessRuleException("Lütfen farklı bir kullanıcı adı oluşturunuz.");
        }
        Optional<SignUp> userFindByEmail = signUpRepository.findByEmail(signUpDTO.getEmail());
        if (userFindByEmail.isPresent()) {
            throw new BusinessRuleException("Lütfen farklı bir mail adresi ile kayıt olunuz.");
        }
        if (!signUpDTO.getEmail().equals(signUpDTO.getReEmail())) {
            throw new BusinessRuleException("Lütfen email adresi ile reEmail adresini doğru giriniz");
        }
        int age = DateTimeHelper.findAgeFromBirthdate(signUpDTO.getBirthdate());
        if (age < 15) {
            throw new BusinessRuleException("Sisteme kaydolabilmeniz için yaşınızın 15'dan büyük olması gerekmektedir");
        }
    }
}

