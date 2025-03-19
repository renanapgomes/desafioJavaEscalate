package com.desafio.xpto.util;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("XPTO API")
						.description("Documentação da API XPTO")
						.version("1.0")
						.contact(new Contact()
								.name("Renan Gomes")
								.email("renan@gmail.com")));
	}
}



