package com.managementSystem.apartmentManagementSystem.service.user.impl;

import com.managementSystem.apartmentManagementSystem.core.dto.GeneralMessageDTO;
import com.managementSystem.apartmentManagementSystem.core.helper.ActivationCodeHelper;
import com.managementSystem.apartmentManagementSystem.core.helper.DateTimeHelper;
import com.managementSystem.apartmentManagementSystem.core.service.MailSenderService;
import com.managementSystem.apartmentManagementSystem.dto.reference.CitiesDTO;
import com.managementSystem.apartmentManagementSystem.dto.user.*;
import com.managementSystem.apartmentManagementSystem.entity.user.Profile;
import com.managementSystem.apartmentManagementSystem.entity.user.User;
import com.managementSystem.apartmentManagementSystem.entity.user.UserStatistics;
import com.managementSystem.apartmentManagementSystem.mapper.reference.CitiesMapper;
import com.managementSystem.apartmentManagementSystem.mapper.user.ProfileMapper;
import com.managementSystem.apartmentManagementSystem.mapper.user.UserMapper;
import com.managementSystem.apartmentManagementSystem.mapper.user.UserStatisticsMapper;
import com.managementSystem.apartmentManagementSystem.repository.reference.CitiesRepository;
import com.managementSystem.apartmentManagementSystem.repository.user.ProfileRepository;
import com.managementSystem.apartmentManagementSystem.repository.user.UserRepository;
import com.managementSystem.apartmentManagementSystem.repository.user.UserStatisticsRepository;
import com.managementSystem.apartmentManagementSystem.service.user.UserBusinessRulesService;
import com.managementSystem.apartmentManagementSystem.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final CitiesMapper citiesMapper;
    private final UserRepository userRepository;

    private final MailSenderService mailSenderService;

    private final UserBusinessRulesService userBusinessRulesService;
    private final UserStatisticsRepository userStatisticsRepository;
    private final CitiesRepository citiesRepository;
    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;

    @Override
    public GeneralMessageDTO signUp(SignUpDTO signUpDTO) {
        User user = userMapper.toEntity(signUpDTO);

        Optional<User> userFindByUsername = userRepository.findByUsername(signUpDTO.getUsername());
        if (userFindByUsername.isPresent()) {
            return new GeneralMessageDTO(0, "Lütfen farklı bir kullanıcı adı oluşturunuz.");
        }

        if (!signUpDTO.getEmail().equals(signUpDTO.getReEmail())) {
            return new GeneralMessageDTO(0, "Lütfen e-posta adresi ile e-posta adresi tekrar alanlarını aynı giriniz.");
        }

        Optional<User> userFindByEmail = userRepository.findByEmail(signUpDTO.getEmail());
        if (userFindByEmail.isPresent()) {
            return new GeneralMessageDTO(0, "Lütfen farklı bir mail adresi ile kayıt olunuz.");
        }

        int age = DateTimeHelper.findAgeFromBirthdate(signUpDTO.getBirthdate());
        if (age < 15) {
            return new GeneralMessageDTO(0, "Sisteme kaydolabilmeniz için 15 yaşından büyük olmanız gerekmektedir.");
        }
        String activationCode = ActivationCodeHelper.generateActivationCode();
        user.setActive(false);
        user.setActivationCode(activationCode);
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setRegistrationTime(LocalDateTime.now());
        userStatistics.setUser(user);
        user.setUserStatistics(userStatistics);

        userRepository.save(user);

        return new GeneralMessageDTO(1, "İşleminiz başarıyla gerçekleştirildi. Lütfen aktivasyon işlemi için e-postanızı kontrol ediniz.");
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

                return new GeneralMessageDTO(1, "Aktivasyon işleminiz başarıyla gerçekleştirilmiştir.");
            }

            return new GeneralMessageDTO(0, "Geçersiz aktivasyon kodu.");

        }
        return new GeneralMessageDTO(0, "Lütfen şifre ve şifre tekrar alanlarını aynı giriniz.");
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
                    return new GeneralMessageDTO(1, "İşleminiz başarıyla gerçekleştirilmiştir.Uygulamaya yönlendiriliyorsunuz.");
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
                    return new GeneralMessageDTO(1, "İşleminiz başarıyla gerçekleştirilmiştir.Uygulamaya yönlendiriliyorsunuz.");
                } else {
                    return new GeneralMessageDTO(0, "İşleminiz başarısız lütfen tekrar deneyiniz.");
                }
            }
        }
        return new GeneralMessageDTO(0, "Kullanıcı bulunamadı.");
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

            return new GeneralMessageDTO(1, "İşleminiz başarıyla gerçekleştirildi. Lütfen aktivasyon işlemi için e-postanızı kontrol ediniz.");
        } else {
            return new GeneralMessageDTO(0, "Girdiğiniz e-posta adresi ile daha önce kayıt oluşturulmamıştır.");
        }
    }

    @Override
    public List<StatisticsDTO> getStatistics() {
         return userStatisticsRepository.getStatistics(true).stream().map(userStatisticsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<CitiesDTO> getAllCitiesList() {
        return citiesRepository.getAllCitiesList().stream().map(citiesMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public GeneralMessageDTO saveOrUpdateProfile(ProfileDTO profileDTO) {
        try {
            Profile profile = profileMapper.toEntity(profileDTO);
            profileRepository.save(profile);
            return new GeneralMessageDTO(1, "İşleminiz başarıyla gerçekleştirildi.");
        } catch (Exception e) {
            e.printStackTrace();
            return new GeneralMessageDTO(0, "İşleminiz gerçekleştirilemedi.");
        }
    }
}



