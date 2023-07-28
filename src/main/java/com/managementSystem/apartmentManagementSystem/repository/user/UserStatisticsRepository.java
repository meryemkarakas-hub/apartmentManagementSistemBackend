package com.managementSystem.apartmentManagementSystem.repository.user;


import com.managementSystem.apartmentManagementSystem.entity.user.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics,Long> {
}
