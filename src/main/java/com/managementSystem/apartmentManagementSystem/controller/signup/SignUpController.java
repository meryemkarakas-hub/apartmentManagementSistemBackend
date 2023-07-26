package com.managementSystem.apartmentManagementSystem.controller.signup;

import com.managementSystem.apartmentManagementSystem.dto.signup.ActivationDTO;
import com.managementSystem.apartmentManagementSystem.dto.signup.ForgotPasswordActivationDTO;
import com.managementSystem.apartmentManagementSystem.dto.signup.LoginDTO;
import com.managementSystem.apartmentManagementSystem.dto.signup.SignUpDTO;
import com.managementSystem.apartmentManagementSystem.service.signup.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

	@RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    ResponseEntity<?> register(@Valid @RequestBody SignUpDTO registerDTO){
        return new ResponseEntity<>(signUpService.signUp(registerDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/activation", method = RequestMethod.POST)
    ResponseEntity<?> activation(@Valid @RequestBody ActivationDTO activationDTO){
        return new ResponseEntity<>(signUpService.activation(activationDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(signUpService.login(loginDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/forgot-password-activation", method = RequestMethod.POST)
    ResponseEntity<?> forgotPasswordActivation(@Valid @RequestBody ForgotPasswordActivationDTO forgotPasswordActivationDTO){
        return new ResponseEntity<>(signUpService.forgotPasswordActivation(forgotPasswordActivationDTO), HttpStatus.OK);
    }
}
