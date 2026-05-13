package com.HealthCare.HealthCare.mapper;

import com.HealthCare.HealthCare.dto.UserDto;
import com.HealthCare.HealthCare.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);
    UserDto toDto(User user);
}
