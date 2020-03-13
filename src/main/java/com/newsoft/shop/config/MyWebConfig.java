package com.newsoft.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer () {
        return new WebMvcConfigurer () {
            @Override
            public void addViewControllers (ViewControllerRegistry registry) {
                registry.addViewController ("/").setViewName ("index");
                registry.addViewController ("/index.html").setViewName ("index");
                registry.addViewController ("/login.html").setViewName ("login");
                registry.addViewController ("/register.html").setViewName ("register");
                registry.addViewController ("/loginSuccess.html").setViewName ("loginSuccess");
                registry.addViewController ("/registerSuccess.html").setViewName ("registerSuccess");
            }
        };
    }
}
