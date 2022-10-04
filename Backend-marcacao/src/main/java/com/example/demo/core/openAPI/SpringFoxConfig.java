package com.example.demo.core.openAPI;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.OAS_30)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.example.demo.api"))
//					.paths(PathSelectors.any())
					.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.apiInfo(apiInfo())
				.tags(new Tag("Pacientes", "Controller que gerencia os pacientes"), 
						new Tag("Médicos", "Controller que gerencia os médicos"));
	}
	
	private List<Response> globalGetResponseMessages() {
		  return Arrays.asList(
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		          .description("Erro interno do Servidor")
		          .build(),
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		          .description("Recurso não possui representação que pode ser aceita pelo consumidor")
		          .build()
		  );
		}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API Gestão de pacientes")
				.description("Documentação de API aberta para médicos e pacientes.")
				.version("1")
				.contact(new Contact("Gabriel Nunes", "https://github.com/gabrielnuunes", "gabrielnuunes22@gmail.com"))
				.build();
	}
}
