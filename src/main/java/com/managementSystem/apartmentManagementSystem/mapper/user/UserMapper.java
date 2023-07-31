package com.managementSystem.apartmentManagementSystem.mapper.user;

import com.managementSystem.apartmentManagementSystem.dto.user.SignUpDTO;
import com.managementSystem.apartmentManagementSystem.dto.user.StatisticsDTO;
import com.managementSystem.apartmentManagementSystem.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;



@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {
    User toEntity(SignUpDTO dto);

}
