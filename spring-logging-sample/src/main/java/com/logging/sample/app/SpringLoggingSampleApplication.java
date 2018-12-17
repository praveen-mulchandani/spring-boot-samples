package com.logging.sample.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.logging.sample.*")
public class SpringLoggingSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringLoggingSampleApplication.class, args);
	}
}
