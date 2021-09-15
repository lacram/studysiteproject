package com.lacram.studysiteproject.controller;

import com.lacram.studysiteproject.dto.SignupRequestDto;
import com.lacram.studysiteproject.model.User;
import com.lacram.studysiteproject.repository.UserRepository;
import com.lacram.studysiteproject.security.JwtTokenProvider;
import com.lacram.studysiteproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/join")
    public Long join(@RequestBody Map<String, String> user) {
        return userRepository.save(User.builder()
                .userid(user.get("userid"))
                .user_pw(passwordEncoder.encode(user.get("user_pw")))
                .user_name(user.get("user_name"))
                .email(user.get("email"))
                .user_identity(Long.parseLong(user.get("user_identity")))
                .build()).getId();
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByUserid(user.get("userid"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디 입니다."));
        if (!passwordEncoder.matches(user.get("user_pw"), member.getUser_pw())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUserid());
    }

    // 회원 조회 test
    @GetMapping("/list")
    public List<User> getUsers(){
        return userRepository.findAll();
    }


}
