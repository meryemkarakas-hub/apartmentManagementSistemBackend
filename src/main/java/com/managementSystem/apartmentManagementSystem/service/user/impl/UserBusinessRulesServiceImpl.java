package com.managementSystem.apartmentManagementSystem.service.user.impl;

import com.managementSystem.apartmentManagementSystem.core.exception.BusinessRuleException;
import com.managementSystem.apartmentManagementSystem.core.helper.DateTimeHelper;
import com.managementSystem.apartmentManagementSystem.dto.user.SignUpDTO;
import com.managementSystem.apartmentManagementSystem.entity.user.User;
import com.managementSystem.apartmentManagementSystem.repository.user.UserRepository;
import com.managementSystem.apartmentManagementSystem.service.user.UserBusinessRulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserBusinessRulesServiceImpl implements UserBusinessRulesService {
    private final UserRepository userRepository;

    @Override
    public void checkRules(SignUpDTO signUpDTO) {
        Optional<User> userFindByUsername = userRepository.findByUsername(signUpDTO.getUsername());
        if (userFindByUsername.isPresent()) {
            throw new BusinessRuleException("Lütfen farklı bir kullanıcı adı oluşturunuz.");
        }
        Optional<User> userFindByEmail = userRepository.findByEmail(signUpDTO.getEmail());
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

