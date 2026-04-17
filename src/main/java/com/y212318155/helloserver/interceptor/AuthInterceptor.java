package com.y212318155.helloserver.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.y212318155.helloserver.util.Result;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头获取token
        String token = request.getHeader("token");

        // 校验token是否有效
        if ("token-admin".equals(token)) {
            return true; // 放行
        }

        // 未登录，返回401
        response.setContentType("application/json;charset=UTF-8");
        Result<String> result = Result.unauthorized("未登录或token无效");
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(result));
        return false; // 拦截
    }
}