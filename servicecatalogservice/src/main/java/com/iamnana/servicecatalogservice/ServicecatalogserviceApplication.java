package com.iamnana.servicecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ServicecatalogserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicecatalogserviceApplication.class, args);
	}

}
