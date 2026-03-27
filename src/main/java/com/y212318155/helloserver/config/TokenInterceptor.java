package com.y212318155.helloserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.y212318155.helloserver.common.Result;
import com.y212318155.helloserver.common.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json;charset=utf-8");

        String uri = request.getRequestURI();
        if (uri.equals("/api/users") || uri.equals("/api/users/login")) {
            return true;
        }

        String token = request.getHeader("token");
        if (token == null || token.trim().isEmpty()) {
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error(ResultCode.NOT_LOGIN)));
            return false;
        }

        return true;
    }
}