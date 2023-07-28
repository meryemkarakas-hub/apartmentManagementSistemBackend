package com.managementSystem.apartmentManagementSystem.mapper.signup;

import com.managementSystem.apartmentManagementSystem.dto.signup.SignUpDTO;
import com.managementSystem.apartmentManagementSystem.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;



@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SignUpMapper {
    User toEntity(SignUpDTO dto);

}
