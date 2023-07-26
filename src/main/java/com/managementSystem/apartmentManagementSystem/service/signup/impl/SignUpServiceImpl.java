package com.managementSystem.apartmentManagementSystem.service.signup.impl;

import com.managementSystem.apartmentManagementSystem.core.helper.ActivationCodeHelper;
import com.managementSystem.apartmentManagementSystem.core.helper.DateTimeHelper;
import com.managementSystem.apartmentManagementSystem.core.service.MailSenderService;
import com.managementSystem.apartmentManagementSystem.dto.signup.ActivationDTO;
import com.managementSystem.apartmentManagementSystem.dto.signup.ForgotPasswordActivationDTO;
import com.managementSystem.apartmentManagementSystem.dto.signup.LoginDTO;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Optional<SignUp> userFindByUsername = signUpRepository.findByUsername(signUpDTO.getUsername());
        if (userFindByUsername.isPresent()) {
            return new GeneralMessageDTO(0, "Lütfen farklı bir kullanıcı adı oluşturunuz.");
        }
        Optional<SignUp> userFindByEmail = signUpRepository.findByEmail(signUpDTO.getEmail());
        if (userFindByEmail.isPresent()) {
            return new GeneralMessageDTO(0, "Lütfen farklı bir mail adresi ile kayıt olunuz.");
        }
        if (!signUpDTO.getEmail().equals(signUpDTO.getReEmail())) {
            return new GeneralMessageDTO(0, "Lütfen email adresi ile reEmail adresini doğru giriniz");
        }
        int age = DateTimeHelper.findAgeFromBirthdate(signUpDTO.getBirthdate());
        if (age < 15) {
            return new GeneralMessageDTO(0, "Sisteme kaydolabilmeniz için yaşınızın 15'dan büyük olması gerekmektedir");
        }
        String activationCode = ActivationCodeHelper.generateActivationCode();
        //String activationUrlContent = activationUrl + activationCode;
        //mailSenderService.sendMail(signUp.getEmail(), "Aktivasyon", ICERIK + activationUrlContent);
        signUp.setActive(false);
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

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public GeneralMessageDTO login(LoginDTO loginDTO) {
        String userNameOrEmail = loginDTO.getUserNameOrEmail();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(loginDTO.getPassword());
        Optional<SignUp> userFindByEmail = signUpRepository.findByEmail(loginDTO.getUserNameOrEmail());
        Optional<SignUp> userFindByUsername = signUpRepository.findByUsername(loginDTO.getUserNameOrEmail());
        if (userFindByUsername.isPresent() || userFindByEmail.isPresent()) {
            if (isValidEmail(userNameOrEmail)) {
                if (userFindByEmail.get().getActive() && isPasswordCorrect(loginDTO.getPassword(), userFindByEmail.get().getPassword())) {
                    return new GeneralMessageDTO(1, "Giriş işleminiz başarıyla gerçekleşmiştir.");
                } else {
                    return new GeneralMessageDTO(0, "İşleminiz başarısız lütfen tekrar deneyiniz.");
                }

            } else {
                if (userFindByUsername.get().getActive() && hashedPassword.equals(userFindByUsername.get().getPassword())) {
                    return new GeneralMessageDTO(1, "Giriş işleminiz başarıyla gerçekleşmiştir.");
                } else {
                    return new GeneralMessageDTO(0, "İşleminiz başarısız lütfen tekrar deneyiniz.");
                }
            }
        }
        return new GeneralMessageDTO(0, "Kullanıcı bulunamadı");
    }


    private boolean isPasswordCorrect(String enteredPassword, String hashedPasswordFromDB) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, hashedPasswordFromDB);
    }

    @Override
    public GeneralMessageDTO forgotPasswordActivation(ForgotPasswordActivationDTO forgotPasswordActivationDTO) {
        Optional<SignUp> userFindByEmail = signUpRepository.findByEmail(forgotPasswordActivationDTO.getEmail());

        if (userFindByEmail.isPresent()) {
            SignUp existingUser = userFindByEmail.get();
            String activationCode = ActivationCodeHelper.generateActivationCode();
            //String activationUrlContent = activationUrl + activationCode;
            //mailSenderService.sendMail(existingUser.getEmail(), "Aktivasyon", ICERIK + activationUrlContent);
            existingUser.setActivationCode(activationCode);
            existingUser.setActive(false);
            signUpRepository.save(existingUser);

            return new GeneralMessageDTO(1, "İşleminiz başarıyla gerçekleştirildi. Lütfen aktivasyon için mail adresinizi kontrol ediniz.");
        } else {
            return new GeneralMessageDTO(0, "Girilen mail adresi ile daha önce kayıt oluşturulmamıştır.");
        }
    }

}



