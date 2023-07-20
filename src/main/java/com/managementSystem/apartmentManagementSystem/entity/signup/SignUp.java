package com.managementSystem.apartmentManagementSystem.entity.signup;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sign_up")
@Getter
@Setter
@NoArgsConstructor
public class SignUp {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
	
    @Column(name = "username",unique = true)
    private String username;
    
    @Column(name = "email",unique = true)
    private String email;
    
    @Column(name = "birthday")
    private LocalDate birthdate;
    
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "activation_code",unique = true)
    private String activationCode;

    @Column(name = "password")
    private String password;
}
