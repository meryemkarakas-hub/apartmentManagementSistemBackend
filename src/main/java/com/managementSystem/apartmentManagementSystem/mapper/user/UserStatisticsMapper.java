package com.managementSystem.apartmentManagementSystem.mapper.user;

import com.managementSystem.apartmentManagementSystem.dto.user.StatisticsDTO;
import com.managementSystem.apartmentManagementSystem.entity.user.UserStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserStatisticsMapper {
    @Mappings({
            @Mapping(source = "user.username", target = "username"),
            @Mapping(source = "user.email", target = "email"),
    })
    StatisticsDTO toDto(UserStatistics entity);
}
