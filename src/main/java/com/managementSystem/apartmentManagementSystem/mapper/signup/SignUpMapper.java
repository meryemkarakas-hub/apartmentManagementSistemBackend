package com.managementSystem.apartmentManagementSystem.mapper.signup;

import com.managementSystem.apartmentManagementSystem.dto.signup.SignUpDTO;
import com.managementSystem.apartmentManagementSystem.entity.signup.SignUp;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;



@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SignUpMapper {

    SignUp toEntity(SignUpDTO dto);

    SignUpDTO toDto(SignUp entity);
    
}