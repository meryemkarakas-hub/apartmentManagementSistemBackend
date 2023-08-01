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

    @Column(name="il_kodu", nullable=false)
    private Integer ilKodu;

    @Column(name="ad")
    private String ad;

    @Column(name="sira")
    private Integer sira;
}
