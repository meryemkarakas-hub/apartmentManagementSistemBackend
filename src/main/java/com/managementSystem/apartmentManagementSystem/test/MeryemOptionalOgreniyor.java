package com.managementSystem.apartmentManagementSystem.test;

import com.managementSystem.apartmentManagementSystem.core.exception.BusinessRuleException;
import com.managementSystem.apartmentManagementSystem.entity.signup.SignUp;
import com.managementSystem.apartmentManagementSystem.repository.signup.SignUpRepository;

import java.util.Optional;


public class MeryemOptionalOgreniyor {

    private static SignUpRepository signUpRepository = null;

    public MeryemOptionalOgreniyor(SignUpRepository signUpRepository) {
        this.signUpRepository = signUpRepository;
    }

    public static void main(String[] args) {
        Optional<SignUp> signUpOptional =  signUpRepository.findByUsername("Meryem");

        //1. seçenek
        if(signUpOptional.isPresent()) {
            signUpOptional.get().setActive(true);
        }
        //2. seçenek
        if(signUpOptional.isPresent()) {
            SignUp signUp = signUpOptional.get();
            signUp.setActive(true);
        }

        //3.seçenek
        if(signUpOptional.isPresent()){
            SignUp signUp = signUpOptional.get();
        }else{
            throw new BusinessRuleException("Kullanıcı bulunamadı!");
        }

        //4.Seçenek
        SignUp signUp2 = signUpOptional.orElse(null);

        //5.seçenek 4. seçeneğin diğer hali
        SignUp signUp3 = signUpOptional.orElseGet(()-> null);

        //6.Seçenek
        signUpOptional.ifPresent(signUp -> signUp.setActive(true));

        //7.Seçenek 3. seçeneğin diğer hali
        SignUp signUp4  = signUpOptional.orElseThrow(()-> new BusinessRuleException("Kullanıcı bulunamadı!"));
    }
}
