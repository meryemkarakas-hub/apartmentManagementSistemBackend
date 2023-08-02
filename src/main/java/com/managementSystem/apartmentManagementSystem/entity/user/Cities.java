package com.managementSystem.apartmentManagementSystem.entity.user;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "cities")
public class Cities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="city_code", nullable=false)
    private Integer cityCode;

    @Column(name="city_name")
    private String cityName;

    @Column(name="sequence")
    private Integer sequence;
}
