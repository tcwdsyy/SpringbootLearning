package com.syy.springbootlearning;

import com.syy.springbootlearning.interceptor.LoginInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;

@SpringBootApplication
@MapperScan("com.syy.springbootlearning.mapper")
@Configuration
public class SpringbootLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLearningApplication.class, args);
    }

//    @Bean
//    public MappedInterceptor loginInterceptor() {
//        return new MappedInterceptor(new String[]{"/**"}, new String[]{"/account/loginForm","/account/login"}, new LoginInterceptor());
//    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Autowired
            LoginInterceptor loginInterceptor;

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                // Register your interceptors here
                registry.addInterceptor(loginInterceptor)
                        .addPathPatterns("/**")
                        .excludePathPatterns("/loginForm","/login");
            }
        };
    }
}
