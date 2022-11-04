package com.singer.infrastructure.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/sm01/joinPage")
            .excludePathPatterns("/sm01")
            .excludePathPatterns("/login")
            .excludePathPatterns("/sessionNotExist")
            .excludePathPatterns("/sse/*");
    }
}
