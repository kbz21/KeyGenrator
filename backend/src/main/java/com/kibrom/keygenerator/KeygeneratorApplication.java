package com.kibrom.keygenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KeygeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeygeneratorApplication.class, args);
	}

}