package br.com.cotiinformatica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication(scanBasePackages="br.com.cotiinformatica.controllers")
public class ProjetoApiStartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoApiStartApplication.class, args);
	}

}
