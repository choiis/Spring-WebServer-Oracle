package com.singer.common.bean;

import com.fasterxml.jackson.core.JsonEncoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class ViewConfigs {

    @Bean(name = "viewNameResolver")
    public BeanNameViewResolver nameViewResolver() {
        BeanNameViewResolver nameViewResolver = new BeanNameViewResolver();
        nameViewResolver.setOrder(0);
        return nameViewResolver;
    }

    @Bean(name = "jsonView")
    public MappingJackson2JsonView jsonView() {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setEncoding(JsonEncoding.UTF8);
        jsonView.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return jsonView;
    }

}
