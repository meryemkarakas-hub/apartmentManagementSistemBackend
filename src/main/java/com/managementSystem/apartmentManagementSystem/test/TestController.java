package com.managementSystem.apartmentManagementSystem.test;

import com.managementSystem.apartmentManagementSystem.core.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/test", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TestController {

    private final MailSenderService mailSenderService;

    @RequestMapping(value = "/mail-gonder", method = RequestMethod.POST)
    ResponseEntity<?> mailGonder() {
        mailSenderService.sendMail("erengumus5151@gmail.com", "Deneme","Bu bir mail içeriğidir");
        return new ResponseEntity<>("Başarılı", HttpStatus.OK);
    }
}
