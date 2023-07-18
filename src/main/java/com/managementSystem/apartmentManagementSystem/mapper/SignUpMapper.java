package com.managementSystem.apartmentManagementSystem.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import com.managementSystem.apartmentManagementSystem.dto.SignUpDTO;
import com.managementSystem.apartmentManagementSystem.entity.SignUp;



@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class SignUpMapper {
	public abstract List<SignUpDTO> toDtoList(List<SignUp> entity);
	
    public abstract SignUp toEntity(SignUpDTO dto);

    public abstract SignUpDTO toDto(SignUp entity);
    
}
