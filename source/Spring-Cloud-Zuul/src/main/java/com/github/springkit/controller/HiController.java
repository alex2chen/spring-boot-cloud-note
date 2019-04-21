package com.github.springkit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    @GetMapping("/test/hello")
    public String hello() {
        return "hello zuul";
    }
}
