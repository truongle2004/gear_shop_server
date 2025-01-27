package com.example.jwt;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// TODO: setup config for .env
@SpringBootApplication(scanBasePackages = "com.example.jwt")
public class TechShopApplication {

	// @Bean
	// public Dotenv dotenv() {
	// return Dotenv.load();
	// }

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(TechShopApplication.class, args);
	}

}
