package com.managementSystem.apartmentManagementSystem.repository.user;

import com.managementSystem.apartmentManagementSystem.entity.user.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {
}
