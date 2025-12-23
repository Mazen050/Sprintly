package com.sprintly.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.sprintly.backend.dtos.RegisterUserRequest;
import com.sprintly.backend.dtos.UpdateUserRequest;
import com.sprintly.backend.dtos.UserDto;
import com.sprintly.backend.entities.Users;


//*
// User Mapper
// Converst User entity to a UserDto */
@Mapper(componentModel="spring")
public interface UserMapper {
    UserDto toDto(Users user);
    Users toEntity(RegisterUserRequest request);

    void update(UpdateUserRequest userDto, @MappingTarget Users user);
}
