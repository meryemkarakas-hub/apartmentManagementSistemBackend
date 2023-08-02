package com.managementSystem.apartmentManagementSystem.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
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

    @Column(name = "active")
    private Boolean active;

    @Column(name = "activation_code",unique = true)
    private String activationCode;

    @Column(name = "password")
    private String password;

    @JsonIgnore
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private UserStatistics userStatistics;
}
