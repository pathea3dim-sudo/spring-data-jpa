package com.example.ecommerceiteapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableConfigurationProperties
@EnableSpringDataWebSupport
@SpringBootApplication
public class EcommerceIteAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceIteAppApplication.class, args);
    }

}
