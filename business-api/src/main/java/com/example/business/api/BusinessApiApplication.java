package com.example.business.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BusinessApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApiApplication.class, args);
    }

}
