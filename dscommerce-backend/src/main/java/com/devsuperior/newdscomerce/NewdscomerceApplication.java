package com.devsuperior.newdscomerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class NewdscomerceApplication {
	public static void main(String[] args) {
		SpringApplication.run(NewdscomerceApplication.class, args);
	}

}