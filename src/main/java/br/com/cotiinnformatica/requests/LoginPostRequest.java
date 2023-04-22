package br.com.cotiinnformatica.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginPostRequest {
	
	private String login;
	private String senha;

}
