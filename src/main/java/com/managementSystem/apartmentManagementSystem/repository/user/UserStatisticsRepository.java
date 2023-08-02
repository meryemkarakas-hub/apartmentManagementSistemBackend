package com.managementSystem.apartmentManagementSystem.repository.user;


import com.managementSystem.apartmentManagementSystem.entity.user.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics,Long> {
    @Query("select distinct u from UserStatistics u join u.user y where u.user.id=y.id and u.user.active=:active")
    List<UserStatistics> getStatistics(@Param("active") Boolean active);
}
