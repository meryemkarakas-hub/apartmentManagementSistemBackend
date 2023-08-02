package com.managementSystem.apartmentManagementSystem.controller.user;

import com.managementSystem.apartmentManagementSystem.dto.user.*;
import com.managementSystem.apartmentManagementSystem.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    ResponseEntity<?> register(@Valid @RequestBody SignUpDTO registerDTO) {
        return new ResponseEntity<>(userService.signUp(registerDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/activation")
    ResponseEntity<?> activation(@Valid @RequestBody ActivationDTO activationDTO){
        return new ResponseEntity<>(userService.activation(activationDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(userService.login(loginDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/forgot-password-activation")
    ResponseEntity<?> forgotPasswordActivation(@Valid @RequestBody ForgotPasswordActivationDTO forgotPasswordActivationDTO){
        return new ResponseEntity<>(userService.forgotPasswordActivation(forgotPasswordActivationDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/statistics")
    public ResponseEntity<List<StatisticsDTO>> getStatistics() {
        List<StatisticsDTO> statisticsDtoList = userService.getStatistics();
        return new ResponseEntity<>(statisticsDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "/profile")
    ResponseEntity<?> register(@Valid @RequestBody ProfileDTO profileDTO){
        return new ResponseEntity<>(userService.saveOrUpdateProfile(profileDTO), HttpStatus.OK);
    }
}
