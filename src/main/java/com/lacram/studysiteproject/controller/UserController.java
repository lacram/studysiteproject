package com.lacram.studysiteproject.controller;

import com.lacram.studysiteproject.dto.SignupRequestDto;
import com.lacram.studysiteproject.model.Response;
import com.lacram.studysiteproject.model.User;
import com.lacram.studysiteproject.repository.UserRepository;
import com.lacram.studysiteproject.security.AuthService;
import com.lacram.studysiteproject.security.CookieUtil;
import com.lacram.studysiteproject.security.JwtUtil;
import com.lacram.studysiteproject.security.RedisUtil;
import com.lacram.studysiteproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;

    // 회원가입
    @PostMapping("/signup")
    public Long join(@RequestBody SignupRequestDto requestDto) {
        System.out.println(requestDto.getUser_id());
        System.out.println(requestDto.getUser_pw());
        return userRepository.save(User.builder()
                .userid(requestDto.getUser_id())
                .userpw(passwordEncoder.encode(requestDto.getUser_pw()))
                .username(requestDto.getUser_name())
                .email(requestDto.getEmail())
                .useridentity(requestDto.getUser_identity())
                .build()).getId();
    }

    // 로그인
    @PostMapping("/login")
    public Response login(@RequestBody SignupRequestDto requestDto,
                          HttpServletRequest req,
                          HttpServletResponse res) {
        try {
            final User user = authService.loginUser(requestDto.getUser_id(), requestDto.getUser_pw());
            final String token = jwtUtil.generateToken(user);
            final String refreshJwt = jwtUtil.generateRefreshToken(user);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            //redisUtil.setDataExpire(refreshJwt, user.getUsername(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
            res.addCookie(accessToken);
            res.addCookie(refreshToken);
            return new Response("success", "로그인에 성공했습니다.", token);
        } catch (Exception e) {
            return new Response("error", "로그인에 실패했습니다.", e.getMessage());
        }
    }


}
