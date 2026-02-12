package com.example.quiz_1141121;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class, //
	ServletWebSecurityAutoConfiguration.class})
public class Quiz1141121Application {

	public static void main(String[] args) {
		SpringApplication.run(Quiz1141121Application.class, args);
	}

}
