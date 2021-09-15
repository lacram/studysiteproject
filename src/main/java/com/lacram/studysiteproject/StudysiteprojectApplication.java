package com.lacram.studysiteproject;

import com.lacram.studysiteproject.model.User;
import com.lacram.studysiteproject.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class StudysiteprojectApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudysiteprojectApplication.class, args);
    }
}
