package com.managementSystem.apartmentManagementSystem.mapper.user;

import com.managementSystem.apartmentManagementSystem.dto.user.ProfileDTO;
import com.managementSystem.apartmentManagementSystem.entity.user.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProfileMapper {
    Profile toEntity(ProfileDTO dto);

}
