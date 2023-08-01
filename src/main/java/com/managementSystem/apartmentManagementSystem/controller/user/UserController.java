package com.managementSystem.apartmentManagementSystem.controller.user;

import com.managementSystem.apartmentManagementSystem.dto.user.*;
import com.managementSystem.apartmentManagementSystem.entity.user.User;
import com.managementSystem.apartmentManagementSystem.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

	@RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    ResponseEntity<?> register(@Valid @RequestBody SignUpDTO registerDTO){
        return new ResponseEntity<>(userService.signUp(registerDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/activation", method = RequestMethod.POST)
    ResponseEntity<?> activation(@Valid @RequestBody ActivationDTO activationDTO){
        return new ResponseEntity<>(userService.activation(activationDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(userService.login(loginDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/forgot-password-activation", method = RequestMethod.POST)
    ResponseEntity<?> forgotPasswordActivation(@Valid @RequestBody ForgotPasswordActivationDTO forgotPasswordActivationDTO){
        return new ResponseEntity<>(userService.forgotPasswordActivation(forgotPasswordActivationDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ResponseEntity<List<StatisticsDTO>> getStatistics() {
        List<StatisticsDTO> statisticsDtoList = userService.getStatistics();
        return new ResponseEntity<>(statisticsDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    ResponseEntity<?> register(@Valid @RequestBody ProfileDTO profileDTO){
        return new ResponseEntity<>(userService.saveOrUpdateProfile(profileDTO), HttpStatus.OK);
    }
}
