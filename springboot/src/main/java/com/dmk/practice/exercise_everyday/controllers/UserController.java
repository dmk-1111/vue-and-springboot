package com.dmk.practice.exercise_everyday.controllers;

import com.dmk.practice.exercise_everyday.dto.UserDto;
import com.dmk.practice.exercise_everyday.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class UserController {

    @GetMapping("/test")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello API123...!");
    }

    @Autowired
    private UserService userService;

    @PostMapping("/create-users")
    public ResponseEntity<?> createUserData(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> listAllUsers(@RequestParam(name = "sort",required = false) String sortBy,
                                          @RequestHeader(name = "x-auth-token") String authToken){
        if(!"12345".equals(authToken)){ //Unauthorize
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.getAllUsers(sortBy));
    }


}
