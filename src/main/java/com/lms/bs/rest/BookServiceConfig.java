package com.lms.bs.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BookServiceConfig {
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
