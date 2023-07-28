package com.managementSystem.apartmentManagementSystem.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.managementSystem.apartmentManagementSystem.entity.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
 Optional<User> findByUsername(String username);
 Optional<User> findByEmail(String email);
 Optional<User> findByActivationCode(String activationCode);

}