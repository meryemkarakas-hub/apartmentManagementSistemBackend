package com.managementSystem.apartmentManagementSystem.mapper.reference;

import com.managementSystem.apartmentManagementSystem.dto.reference.CitiesDTO;
import com.managementSystem.apartmentManagementSystem.dto.user.StatisticsDTO;
import com.managementSystem.apartmentManagementSystem.entity.user.Cities;
import com.managementSystem.apartmentManagementSystem.entity.user.UserStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CitiesMapper {
    CitiesDTO toDto(Cities entity);
}
