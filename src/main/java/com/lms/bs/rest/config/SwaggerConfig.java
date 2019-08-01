package com.lms.bs.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.lms"))
				.paths(PathSelectors.regex("/.*"))
				.build()
				.apiInfo(apiEndPointsInfo());
	}

	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder()
				.title("BOOK SERVICE REST API")
				.description("Rest API to manage books in the library")
				.contact(new Contact("Arnab Mondal", "http://github.com/arnab2807", "arnab2807@gmail.com"))
				.license("Freeware")
				.version("1.0.0")
				.build();
	}
}
