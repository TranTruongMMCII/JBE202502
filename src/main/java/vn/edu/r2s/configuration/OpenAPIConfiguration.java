package vn.edu.r2s.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfiguration {

	@Bean
	SecurityScheme createAPIKeyScheme() {
	    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
	        .bearerFormat("JWT")
	        .scheme("bearer");
	}
	
	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
		        .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
		        .info(new Info()
				.title("Demo JBE-02-2025 API")
				.contact(new Contact().name("Truong").email("truong@gmail.com"))
				.version("1.0.0")
				.description("This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3."));
	}

}
