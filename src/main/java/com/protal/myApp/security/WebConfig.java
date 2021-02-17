package com.protal.myApp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Authorization","Access-Control-Allow-Origin")
                .allowCredentials(false).maxAge(3600);
    }
}
