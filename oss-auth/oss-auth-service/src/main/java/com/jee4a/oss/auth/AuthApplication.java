package com.jee4a.oss.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class AuthApplication{
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
 		