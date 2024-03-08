package com.example.business.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuccessController {
    @GetMapping("/success")
    public String healthTest() {
        return "success";
    }
}
