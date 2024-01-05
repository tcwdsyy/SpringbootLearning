package com.syy.springbootlearning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@MapperScan("com.syy.springbootlearning.mapper")
@Configuration
public class SpringbootLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLearningApplication.class, args);
    }

}
