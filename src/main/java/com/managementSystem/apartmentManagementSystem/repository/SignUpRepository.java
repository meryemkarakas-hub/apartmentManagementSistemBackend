package com.managementSystem.apartmentManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.managementSystem.apartmentManagementSystem.entity.SignUp;

@Repository
public interface SignUpRepository extends JpaRepository<SignUp,Long>{

}
