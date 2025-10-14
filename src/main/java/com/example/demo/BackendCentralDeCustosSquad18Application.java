package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients(basePackages = "com.example.demo.domain.services.email")
public class BackendCentralDeCustosSquad18Application {

	public static void main(String[] args) {
		SpringApplication.run(BackendCentralDeCustosSquad18Application.class, args);
	}

}
