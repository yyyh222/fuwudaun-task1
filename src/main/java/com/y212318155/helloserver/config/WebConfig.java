package com.y212318155.helloserver.config;

import com.y212318155.helloserver.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/api/**") // 拦截 /api 下所有接口
                .excludePathPatterns(
                        "/api/users/login",   // 放行登录接口
                        "/api/users"      // 放行新增用户接口
                );
    }
}