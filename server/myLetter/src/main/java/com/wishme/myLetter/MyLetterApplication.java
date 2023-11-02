package com.wishme.myLetter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MyLetterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyLetterApplication.class, args);
	}

}
