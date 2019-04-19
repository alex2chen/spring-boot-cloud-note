package com.github.springkit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSyncApplication.class, args);
	}
}
