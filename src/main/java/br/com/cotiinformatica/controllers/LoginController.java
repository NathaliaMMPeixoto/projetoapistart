package br.com.cotiinformatica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.IUsuarioRepository;
import br.com.cotiinformatica.security.MD5Cryptography;
import br.com.cotiinnformatica.requests.LoginPostRequest;
import io.swagger.annotations.ApiOperation;
import jakarta.transaction.Transactional;

@Controller
@Transactional
public class LoginController {

	@Autowired
	private IUsuarioRepository usuarioRepository;

	private static final String ENDPOINT = "/api/login";

	@ApiOperation("Servico autenticaçao de usuario")
	@PostMapping(ENDPOINT)
	@CrossOrigin
	public ResponseEntity<String> post(@RequestBody LoginPostRequest request) {

		try {

			// pesquisar no banco de dados o usuario atraves do login e senha

			Usuario usuario = usuarioRepository.findByLoginAndSenha(request.getLogin(), MD5Cryptography.encrypt(request.getSenha()));

			// verificar se o usuario foi encontrado
			if (usuario != null) {

				// TODO

				return ResponseEntity.status(HttpStatus.OK).body("Usuario autenticado com sucesso");
			} else {

				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
			}

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

}
