package com.managementSystem.apartmentManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.managementSystem.apartmentManagementSystem.configuration.CorsConfig;

@SpringBootApplication
@Import(CorsConfig.class)
public class ApartmentManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApartmentManagementSystemApplication.class, args);
	}

}
