package com.careerfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CareerfitApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerfitApplication.class, args);
	}

}
