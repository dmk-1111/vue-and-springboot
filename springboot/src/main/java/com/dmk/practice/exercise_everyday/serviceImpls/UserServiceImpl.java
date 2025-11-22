package com.dmk.practice.exercise_everyday.serviceImpls;

import com.dmk.practice.exercise_everyday.dto.UserDto;
import com.dmk.practice.exercise_everyday.mapper.UserMapper;
import com.dmk.practice.exercise_everyday.models.User;
import com.dmk.practice.exercise_everyday.repositories.UserRepository;
import com.dmk.practice.exercise_everyday.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User createUser(UserDto userDto) {
        return userRepository.save(userMapper.toUser(userDto));
    }

    @Override
    public Iterable<UserDto> getAllUsers(String sort) {

        if(!Set.of("username","password","email").contains(sort))
            sort = "email";

        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto).toList();
    }

}
