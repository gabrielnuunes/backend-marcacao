package com.example.demo.core.openAPI;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.example.demo.api.exceptionhandler.Problem;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.Tag;

@Configuration
public class SpringFoxConfig {

	TypeResolver typeResolver = new TypeResolver();
	
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.OAS_30)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.example.demo.api"))
					.paths(PathSelectors.any())
					.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.apiInfo(apiInfo())
				.tags(new Tag("Pacientes", "Controller que gerencia os pacientes"));
	}
	
	@Bean
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}
	
	private List<Response> globalGetResponseMessages() {
	    return Arrays.asList(
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
	                    .description("Erro interno do Servidor")
	                    .representation( MediaType.APPLICATION_JSON )
	                    .apply(getProblemaModelReference())
	                    .build(),
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
	                    .description("Recurso não possui representação que pode ser aceita pelo consumidor")
	                    .build()
	    );
	}
	
	private List<Response> globalPostPutResponseMessages() {
	    return Arrays.asList(
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
	                    .description("Requisição inválida (erro do cliente)")
	                    .representation( MediaType.APPLICATION_JSON )
	                    .apply(getProblemaModelReference())
	                    .build(),
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
	                    .description("Erro interno no servidor")
	                    .representation( MediaType.APPLICATION_JSON )
	                    .apply(getProblemaModelReference())
	                    .build(),
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
	                    .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
	                    .build(),
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
	                    .description("Requisição recusada porque o corpo está em um formato não suportado")
	                    .representation( MediaType.APPLICATION_JSON )
	                    .apply(getProblemaModelReference())
	                    .build()
	    );
	}
	
	private List<Response> globalDeleteResponseMessages() {
	    return Arrays.asList(
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
	                    .description("Requisição inválida (erro do cliente)")
	                    .representation( MediaType.APPLICATION_JSON )
	                    .apply(getProblemaModelReference())
	                    .build(),
	            new ResponseBuilder()
	                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
	                    .description("Erro interno no servidor")
	                    .representation( MediaType.APPLICATION_JSON )
	                    .apply(getProblemaModelReference())
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
	
	
	private Consumer<RepresentationBuilder> getProblemaModelReference() {
	    return r -> r.model(m -> m.name("Problema")
	            .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
	                    q -> q.name("Problema").namespace("com.example.demo.api.exceptionhandler")))));
	}
	
}
