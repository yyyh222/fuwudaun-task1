package com.y212318155.helloserver.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头获取 Authorization 令牌
        String token = request.getHeader("Authorization");

        // 未携带令牌 → 拦截并返回 401
        if (token == null || token.isEmpty()) {
            response.setContentType("application/json;charset=UTF-8");
            String errorJson = "{\"code\": 401, \"msg\": \"登录凭证已缺失，请重新登录\"}";
            response.getWriter().write(errorJson);
            return false;
        }

        // 携带令牌 → 放行
        return true;
    }
}