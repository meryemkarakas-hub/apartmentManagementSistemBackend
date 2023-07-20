package com.managementSystem.apartmentManagementSystem.service.signup.impl;

import com.managementSystem.apartmentManagementSystem.core.helper.ActivationCodeHelper;
import com.managementSystem.apartmentManagementSystem.core.service.MailSenderService;
import com.managementSystem.apartmentManagementSystem.dto.signup.ActivationDTO;
import com.managementSystem.apartmentManagementSystem.service.signup.SignUpBusinessRulesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.managementSystem.apartmentManagementSystem.core.dto.GeneralMessageDTO;
import com.managementSystem.apartmentManagementSystem.dto.signup.SignUpDTO;
import com.managementSystem.apartmentManagementSystem.entity.signup.SignUp;

import com.managementSystem.apartmentManagementSystem.mapper.signup.SignUpMapper;
import com.managementSystem.apartmentManagementSystem.repository.signup.SignUpRepository;
import com.managementSystem.apartmentManagementSystem.service.signup.SignUpService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SignUpServiceImpl implements SignUpService {
    @Value("${origin.activation-url}")
    private String activationUrl;

    private static final String ICERIK = "Aktivasyon işleminizi gerçekleştirmek için lütfen linke tıklanıyız. ";
    private final SignUpMapper signUpMapper;

    private final SignUpRepository signUpRepository;
    private final MailSenderService mailSenderService;

    private final SignUpBusinessRulesService signUpBusinessRulesService;

    @Override
    public GeneralMessageDTO signUp(SignUpDTO signUpDTO) {
        SignUp signUp = signUpMapper.toEntity(signUpDTO);

        signUpBusinessRulesService.checkRules(signUpDTO);

        String activationCode = ActivationCodeHelper.generateActivationCode();
        String activationUrlContent = activationUrl + activationCode;
        mailSenderService.sendMail(signUp.getEmail(), "Aktivasyon", ICERIK + activationUrlContent);

        signUp.setActivationCode(activationCode);
        signUpRepository.save(signUp);

        return new GeneralMessageDTO(1, "İşleminiz başarıyla gerçekleştirildi.Lütfen aktivasyon için mail adresinizi kontrol ediniz.");
    }

    @Override
    public GeneralMessageDTO activation(ActivationDTO activationDTO) {
        String password = activationDTO.getPassword();
        String rePassword = activationDTO.getRePassword();
        if (password.equals(rePassword)) {
            Optional<SignUp> userFindByActivationCode = signUpRepository.findByActivationCode(activationDTO.getActivationCode());
            if (userFindByActivationCode.isPresent() && !userFindByActivationCode.get().getActive()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashedPassword = encoder.encode(activationDTO.getPassword());
                userFindByActivationCode.get().setRegistrationDate(LocalDateTime.now());
                userFindByActivationCode.get().setPassword(hashedPassword);
                userFindByActivationCode.get().setActive(true);
                signUpRepository.save(userFindByActivationCode.get());
                return new GeneralMessageDTO(1, "Aktivasyon İşleminiz başarıyla gerçekleşmiştir.");
            }

            return new GeneralMessageDTO(0, "Geçersiz aktivasyon kodu");

        }
        return new GeneralMessageDTO(0, "Lütfen password ve repassword alanlarını aynı giriniz.");

    }


}




