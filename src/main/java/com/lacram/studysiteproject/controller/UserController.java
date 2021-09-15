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
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Response> login(@RequestBody SignupRequestDto requestDto,
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
            return new ResponseEntity<>(new Response("success", "로그인에 성공했습니다.", token),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response("fail", "로그인에 실패했습니다.", e.getMessage()),HttpStatus.BAD_REQUEST);
            //return ResponseEntity.badRequest().body(new Response("fail", "로그인에 실패했습니다.", e.getMessage()));
        }
    }

    @GetMapping("/user/{userid}/id/exist")
    public ResponseEntity<Response> checkUseridDuplicate(@PathVariable String userid){
        if (userRepository.existsByUserid(userid)) return new ResponseEntity<>(new Response("fail", "이미 존재하는 아이디입니다."), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new Response("success", "사용가능한 아이디입니다."), HttpStatus.OK);
    }

    @GetMapping("/user/{email}/email/exist")
    public ResponseEntity<Response> checkEmailDuplicate(@PathVariable String email){
        if (userRepository.existsByEmail(email)) return new ResponseEntity<>(new Response("fail", "이미 존재하는 이메일입니다."), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new Response("success", "사용가능한 이메일입니다."), HttpStatus.OK);
    }

    @GetMapping("/user/{username}/username/exist")
    public ResponseEntity<Response> checkusernameDuplicate(@PathVariable String username){
        if (userRepository.existsByUsername(username)) return new ResponseEntity<>(new Response("fail", "이미 존재하는 닉네임입니다."), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new Response("success", "사용가능한 닉네임입니다."), HttpStatus.OK);
    }
}
