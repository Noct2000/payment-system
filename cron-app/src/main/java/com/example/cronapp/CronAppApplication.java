package com.example.cronapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CronAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CronAppApplication.class, args);
    }

}
