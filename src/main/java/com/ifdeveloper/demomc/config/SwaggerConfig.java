package com.ifdeveloper.demomc.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.ifdeveloper.demomc.constantes.ResponseMessages;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
	
	private final ResponseMessage recursoCriado = simpleMessage(201, ResponseMessages.RECURSO_CRIADO);
	private final ResponseMessage recursoAtualizado = simpleMessage(204, ResponseMessages.RECURSO_ATUALIZADO);
	private final ResponseMessage recursoDeletado = simpleMessage(204, ResponseMessages.RECURSO_DELETADO);
	private final ResponseMessage recursoNaoAutorizado = simpleMessage(403, ResponseMessages.RECURSO_NAO_AUTORIZADO);
	private final ResponseMessage recursoNaoEncontrado = simpleMessage(404, ResponseMessages.RECURSO_NAO_ENCONTRADO);
	private final ResponseMessage erroValidacao = simpleMessage(422, ResponseMessages.ERRO_VALIDACAO);
	private final ResponseMessage erroInterno = simpleMessage(500, ResponseMessages.ERRO_INTERNO);
	
	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(recursoNaoAutorizado, recursoNaoEncontrado, erroInterno))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(recursoCriado, recursoNaoAutorizado, erroValidacao, erroInterno))
				.globalResponseMessage(RequestMethod.PUT, Arrays.asList(recursoAtualizado, recursoNaoAutorizado, recursoNaoEncontrado, erroValidacao, erroInterno))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(recursoDeletado, recursoNaoAutorizado, recursoNaoEncontrado, erroInterno))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ifdeveloper.demomc.resources"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo("API Ramazon Ecommerce",
				"API Rest - Ecommerce", "Versão 1.0",
				"",
				new Contact("Felipe Maia", "https://github.com/felipesm/demo-mc", "felipe.silwa@gmail.com"),
				"Permitido uso para estudos", "https://github.com/felipesm/demo-mc", Collections.emptyList()
		);
	}
	
	@Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
	
	private ResponseMessage simpleMessage(int code, String msg) {
		return new ResponseMessageBuilder().code(code).message(msg).build();
		}

}
