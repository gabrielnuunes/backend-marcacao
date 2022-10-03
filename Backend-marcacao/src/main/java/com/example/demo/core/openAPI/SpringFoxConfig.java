package com.example.demo.core.openAPI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.OAS_30)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.demo.api"))
				.build()
				.apiInfo(apiInfo());
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API Gestão de pacientes")
				.description("Documentação de API aberta para médicos e pacientes.")
				.version("1")
				.contact(new Contact("Gabriel Nunes", "https://github.com/gabrielnuunes", "gabrielnuunes22@gmail.com"))
				.build();
	}
}
