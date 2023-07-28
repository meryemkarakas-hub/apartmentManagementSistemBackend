package com.managementSystem.apartmentManagementSystem.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_statistics")
@Getter
@Setter
@NoArgsConstructor
public class UserStatistics {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "registration_time")
    private LocalDateTime registrationTime;

    @Column(name = "activation_time")
    private LocalDateTime activationTime;

    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

}
