package com.managementSystem.apartmentManagementSystem.test;

import com.managementSystem.apartmentManagementSystem.core.exception.BusinessRuleException;
import com.managementSystem.apartmentManagementSystem.entity.user.User;
import com.managementSystem.apartmentManagementSystem.repository.user.UserRepository;

import java.util.Optional;


public class MeryemOptionalOgreniyor {

    private static UserRepository userRepository = null;

    public MeryemOptionalOgreniyor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        Optional<User> signUpOptional =  userRepository.findByUsername("Meryem");

        //1. seçenek
        if(signUpOptional.isPresent()) {
            signUpOptional.get().setActive(true);
        }
        //2. seçenek
        if(signUpOptional.isPresent()) {
            User user = signUpOptional.get();
            user.setActive(true);
        }

        //3.seçenek
        if(signUpOptional.isPresent()){
            User user = signUpOptional.get();
        }else{
            throw new BusinessRuleException("Kullanıcı bulunamadı!");
        }

        //4.Seçenek
        User user2 = signUpOptional.orElse(null);

        //5.seçenek 4. seçeneğin diğer hali
        User user3 = signUpOptional.orElseGet(()-> null);

        //6.Seçenek
        signUpOptional.ifPresent(signUp -> signUp.setActive(true));

        //7.Seçenek 3. seçeneğin diğer hali
        User user4 = signUpOptional.orElseThrow(()-> new BusinessRuleException("Kullanıcı bulunamadı!"));
    }
}
