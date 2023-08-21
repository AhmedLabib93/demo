package com.banking.demo;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Banking Demo API",
				description = "Banking Technical Task APIs Documentation",
				version = "1.0",
				contact = @Contact(name = "Ahmed", email = "ahmed.labib@andela.com"),
				license = @License(name="Apache 2.0")),
		externalDocs = @ExternalDocumentation(description = "Spring Boot App Documentation"))
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
