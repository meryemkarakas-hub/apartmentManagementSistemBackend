package com.managementSystem.apartmentManagementSystem.repository.signup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.managementSystem.apartmentManagementSystem.entity.signup.SignUp;

import java.util.Optional;

@Repository
public interface SignUpRepository extends JpaRepository<SignUp,Long>{
 Optional<SignUp> findByUsername(String username);
 Optional<SignUp> findByEmail(String email);

 Optional<SignUp> findByActivationCode(String activationCode);
}
