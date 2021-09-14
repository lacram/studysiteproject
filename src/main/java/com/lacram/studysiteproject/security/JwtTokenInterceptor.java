package com.lacram.studysiteproject.security;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws IOException {
        final String header = request.getHeader("Authorization");
        if (header != null) {
            final String token = JwtTokenProvider.getTokenFromHeader(header);
            if (JwtTokenProvider.validateToken(token)) {
                return true;
            }
        }
        response.sendRedirect("/error/unauthorized");
        return false;
    }
}


