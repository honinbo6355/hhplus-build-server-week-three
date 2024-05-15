package com.example.hhplus.reservation.config;

import com.example.hhplus.reservation.interceptor.AuthInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/v3/api-docs/**",
                        "/v3/api-docs.yaml",
                        "/swagger-ui/**",
                        "/api/users/token",
                        "/api/users/{userId}/token",
                        "/api/users/point",
                        "/api/users/{userId}/point",
                        "/api/sample/**");
    }
}
