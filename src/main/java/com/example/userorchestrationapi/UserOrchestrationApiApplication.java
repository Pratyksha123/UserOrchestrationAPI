package com.example.userorchestrationapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "User API", version = "1.0", description = "API documentation for User Service"))
public class UserOrchestrationApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(UserOrchestrationApiApplication.class, args);
    }



}
