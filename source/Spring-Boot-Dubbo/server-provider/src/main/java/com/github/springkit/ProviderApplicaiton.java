package com.github.springkit;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class ProviderApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplicaiton.class, args);
        System.out.println("complete");
    }
}
