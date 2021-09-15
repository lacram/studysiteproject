package com.lacram.studysiteproject.controller;

import com.lacram.studysiteproject.dto.SignupRequestDto;
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
    public ResponseEntity<String> login(@RequestBody Map<String, String> user,
                                HttpServletRequest req,
                                HttpServletResponse res) {

            System.out.println(0);
            final User member = authService.loginUser(user.get("userid"), user.get("user_pw"));
            System.out.println(5);
            final String token = jwtUtil.generateToken(member);
            System.out.println(1);
            final String refreshJwt = jwtUtil.generateRefreshToken(member);
            System.out.println(2);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            System.out.println(3);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            System.out.println(4);
            redisUtil.setDataExpire(refreshJwt, member.getUserid(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
            System.out.println(6);
            res.addCookie(accessToken);
            System.out.println(7);
            res.addCookie(refreshToken);
            System.out.println(8);
            return ResponseEntity.ok(token);

//        catch (Exception e) {
//            System.out.println(-1);
//            return ResponseEntity.badRequest().build();
//        }
    }


    // 회원 조회 test
    @GetMapping("/list")
    public List<User> getUsers(){
        return userRepository.findAll();
    }


}
