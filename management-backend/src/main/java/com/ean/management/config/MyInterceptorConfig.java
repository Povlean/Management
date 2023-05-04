package com.ean.management.config;

import com.ean.management.interceptor.JwtValidateInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @description:TODO
 * @author:Povlean
 */
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {
    @Resource
    private JwtValidateInterceptor jwtValidateInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(jwtValidateInterceptor);
        interceptorRegistration.addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/user/logout",
                        "/user/info",
                        "/error",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/**"
                );
    }
}
