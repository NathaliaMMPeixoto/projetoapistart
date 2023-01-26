package br.com.cotiinformatica.config;


 import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfiguration{

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.cotiinformatica")).paths(PathSelectors.ant("/**"))
				.build().apiInfo(apiInfo());

	}

	private ApiInfo apiInfo() {
		return new ApiInfo("API para controle de produtos",
				"Sistema de API desenvolvido em SpringBoot com Hibernate e JPA", "Versao 1.0",
			"http://www.cotiinformatica.com.br",
			new Contact("COTI Informatica", "http://www.cotiinformatica.com.br", "contato@cotiinformatica.com.br"),
				"Licenca da API", "http://www.cotiinformatica.com.br", Collections.emptyList());
	}
	
	
}
