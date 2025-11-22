package com.dmk.practice.exercise_everyday.services;

import com.dmk.practice.exercise_everyday.dto.UserDto;
import com.dmk.practice.exercise_everyday.models.User;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserService {

    User createUser(UserDto userDto);

    Iterable<UserDto> getAllUsers(String sort);
}
