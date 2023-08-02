package com.managementSystem.apartmentManagementSystem.mapper.reference;

import com.managementSystem.apartmentManagementSystem.dto.reference.CitiesDTO;
import com.managementSystem.apartmentManagementSystem.entity.user.Cities;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CitiesMapper {
    CitiesDTO toDto(Cities entity);
}
