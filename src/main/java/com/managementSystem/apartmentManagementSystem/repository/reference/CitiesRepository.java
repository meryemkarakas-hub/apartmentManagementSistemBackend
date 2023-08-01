package com.managementSystem.apartmentManagementSystem.repository.reference;

import com.managementSystem.apartmentManagementSystem.entity.user.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CitiesRepository extends JpaRepository<Cities,Long> {
    @Query("select u from Cities u order by u.ad asc")
    List<Cities> getAllCitiesList();
}
