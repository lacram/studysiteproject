package com.lacram.studysiteproject.security;

import com.lacram.studysiteproject.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@NoArgsConstructor
@Component
public class JwtUtil {

    public static long TOKEN_VALIDATION_SECOND = 1000L * 10;
    public static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2;

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    public JwtUtil(Long tvs){
        TOKEN_VALIDATION_SECOND = tvs;
    }

    private String SECRET_KEY = "secretkeysecretkeysecretkeysecretkeysecretkeysecretkeysecretkeysecretkey";

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserid(String token) {
        return extractAllClaims(token).get("userid", String.class);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        return doGenerateToken(user.getUserid(), TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(User user) {
        return doGenerateToken(user.getUserid(), REFRESH_TOKEN_VALIDATION_SECOND);
    }

    public String doGenerateToken(String username, long expireTime) {

        Claims claims = Jwts.claims();
        claims.put("userid", username);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserid(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean validateToken(String token) {

        return !isTokenExpired(token);
    }
}