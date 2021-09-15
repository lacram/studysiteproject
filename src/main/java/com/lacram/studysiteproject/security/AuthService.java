package com.lacram.studysiteproject.security;

import com.lacram.studysiteproject.model.User;
import com.lacram.studysiteproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User loginUser(String id, String password) throws Exception {
        System.out.println(id);

        User user = userRepository.findById(6L).orElseThrow(
                () -> new NullPointerException("멤버가 조회되지 않음")
        );
        System.out.println("멤버문제");
        if (!passwordEncoder.matches(password, user.getUser_pw())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        System.out.println("유저 찾음");
        return user;
    }
}