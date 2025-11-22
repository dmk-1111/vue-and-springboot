package com.dmk.practice.exercise_everyday.mapper;

import com.dmk.practice.exercise_everyday.dto.UserDto;
import com.dmk.practice.exercise_everyday.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface UserMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    UserDto toDto(User user);
    User toUser(UserDto userDto);
}
