package com.managementSystem.apartmentManagementSystem.service.user.impl;

import com.managementSystem.apartmentManagementSystem.core.helper.ActivationCodeHelper;
import com.managementSystem.apartmentManagementSystem.core.helper.DateTimeHelper;
import com.managementSystem.apartmentManagementSystem.core.service.MailSenderService;
import com.managementSystem.apartmentManagementSystem.dto.user.*;
import com.managementSystem.apartmentManagementSystem.entity.user.User;
import com.managementSystem.apartmentManagementSystem.entity.user.UserStatistics;
import com.managementSystem.apartmentManagementSystem.mapper.user.UserStatisticsMapper;
import com.managementSystem.apartmentManagementSystem.repository.user.UserStatisticsRepository;
import com.managementSystem.apartmentManagementSystem.service.user.UserBusinessRulesService;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.managementSystem.apartmentManagementSystem.core.dto.GeneralMessageDTO;

import com.managementSystem.apartmentManagementSystem.mapper.user.UserMapper;
import com.managementSystem.apartmentManagementSystem.repository.user.UserRepository;
import com.managementSystem.apartmentManagementSystem.service.user.UserService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    @Value("${origin.activation-url}")
    private String activationUrl;

    private static final String ICERIK = "Aktivasyon işleminizi gerçekleştirmek için lütfen linke tıklanıyız. ";
    private final UserMapper userMapper;

    private final UserStatisticsMapper userStatisticsMapper;

    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;

    private final UserBusinessRulesService userBusinessRulesService;

    private final UserStatisticsRepository userStatisticsRepository;

    @Override
    public GeneralMessageDTO signUp(SignUpDTO signUpDTO) {
        User user = userMapper.toEntity(signUpDTO);

        Optional<User> userFindByUsername = userRepository.findByUsername(signUpDTO.getUsername());
        if (userFindByUsername.isPresent()) {
            return new GeneralMessageDTO(0, "Lütfen farklı bir kullanıcı adı oluşturunuz.");
        }

        Optional<User> userFindByEmail = userRepository.findByEmail(signUpDTO.getEmail());
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
        user.setActive(false);
        user.setActivationCode(activationCode);
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setRegistrationTime(LocalDateTime.now());
        userStatistics.setUser(user);
        user.setUserStatistics(userStatistics);

        userRepository.save(user);

        return new GeneralMessageDTO(1, "İşleminiz başarıyla gerçekleştirildi. Lütfen aktivasyon için mail adresinizi kontrol ediniz.");
    }



    @Override
    public GeneralMessageDTO activation(ActivationDTO activationDTO) {
        String password = activationDTO.getPassword();
        String rePassword = activationDTO.getRePassword();
        if (password.equals(rePassword)) {
            Optional<User> userFindByActivationCode = userRepository.findByActivationCode(activationDTO.getActivationCode());
            if (userFindByActivationCode.isPresent() && !userFindByActivationCode.get().getActive()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashedPassword = encoder.encode(activationDTO.getPassword());
                userFindByActivationCode.get().setPassword(hashedPassword);
                userFindByActivationCode.get().setActive(true);
                UserStatistics userStatistics = userFindByActivationCode.get().getUserStatistics();
                if (userStatistics != null) {
                    userStatistics.setActivationTime(LocalDateTime.now());
                    userStatisticsRepository.save(userStatistics);
                }

                userRepository.save(userFindByActivationCode.get());

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
        Optional<User> userFindByEmail = userRepository.findByEmail(loginDTO.getUserNameOrEmail());
        Optional<User> userFindByUsername = userRepository.findByUsername(loginDTO.getUserNameOrEmail());

        if (userFindByUsername.isPresent() || userFindByEmail.isPresent()) {
            if (isValidEmail(userNameOrEmail)) {
                if (userFindByEmail.get().getActive() && isPasswordCorrect(loginDTO.getPassword(), userFindByEmail.get().getPassword())) {
                    UserStatistics userStatistics = userFindByEmail.get().getUserStatistics();
                    if (userStatistics != null) {
                        userStatistics.setLastLoginTime(LocalDateTime.now());
                        userStatisticsRepository.save(userStatistics);
                    }
                    return new GeneralMessageDTO(1, "Giriş işleminiz başarıyla gerçekleşmiştir.");
                } else {
                    return new GeneralMessageDTO(0, "İşleminiz başarısız lütfen tekrar deneyiniz.");
                }

            } else {
                if (userFindByUsername.get().getActive() && isPasswordCorrect(loginDTO.getPassword(), userFindByUsername.get().getPassword())) {
                    UserStatistics userStatistics = userFindByUsername.get().getUserStatistics();
                    if (userStatistics != null) {
                        userStatistics.setLastLoginTime(LocalDateTime.now());
                        userStatisticsRepository.save(userStatistics);
                    }
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
        Optional<User> userFindByEmail = userRepository.findByEmail(forgotPasswordActivationDTO.getEmail());

        if (userFindByEmail.isPresent()) {
            User existingUser = userFindByEmail.get();
            String activationCode = ActivationCodeHelper.generateActivationCode();
            //String activationUrlContent = activationUrl + activationCode;
            //mailSenderService.sendMail(existingUser.getEmail(), "Aktivasyon", ICERIK + activationUrlContent);
            existingUser.setActivationCode(activationCode);
            existingUser.setActive(false);
            userRepository.save(existingUser);

            return new GeneralMessageDTO(1, "İşleminiz başarıyla gerçekleştirildi. Lütfen aktivasyon için mail adresinizi kontrol ediniz.");
        } else {
            return new GeneralMessageDTO(0, "Girilen mail adresi ile daha önce kayıt oluşturulmamıştır.");
        }
    }

    @Override
    public List<StatisticsDTO> getStatistics() {
      //  List <User> bb= userRepository.getStatistics();
            List <UserStatistics> a= userStatisticsRepository.getStatistics();
                     //.stream().map(userStatisticsMapper::toDto).collect(Collectors.toList());
         return userStatisticsRepository.getStatistics().stream().map(userStatisticsMapper::toDto).collect(Collectors.toList());
      //  return null;
    }

}



