package com.lacram.studysiteproject.controller;

import com.lacram.studysiteproject.dto.SignupRequestDto;
import com.lacram.studysiteproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/api/user/sign_up")
    public Long registerUser(@RequestBody SignupRequestDto requestDto){
        userService.registerUser(requestDto);
        return 1L;
    }
}
