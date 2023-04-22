package br.com.cotiinformatica.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
public class CorsConfiguration  {
	
	
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
		
	}

}
