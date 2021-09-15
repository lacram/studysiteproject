package com.lacram.studysiteproject.service;

import com.lacram.studysiteproject.dto.SignupRequestDto;
import com.lacram.studysiteproject.model.User;
import com.lacram.studysiteproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // 중복 체크, 비번 암호화 필요
//    public void registerUser(SignupRequestDto requestDto) {
//        String user_id = requestDto.getUser_id();
//        String user_pw = requestDto.getUser_pw();
//        String user_name = requestDto.getUser_name();
//        String email = requestDto.getEmail();
//        Long user_identity = requestDto.getUser_identity();
//        User user = new User(user_id,user_pw,user_name,email,user_identity);
//
//        userRepository.save(user);
//    }
}
