package com.example.springemailexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringEmailExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEmailExampleApplication.class, args);
	}

}
