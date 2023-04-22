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
import br.com.cotiinnformatica.requests.AccountPostRequest;
import io.swagger.annotations.ApiOperation;
import jakarta.transaction.Transactional;

@Controller
@Transactional
public class AccountController {
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	private static final String ENDPOINT = "/api/account";
	
	// metodo para realizar o servico de cadastro de usuarios
	    @ApiOperation("Servico para criacao de conta de usuario")
                @PostMapping(ENDPOINT)
		@CrossOrigin
		public ResponseEntity<String> post(@RequestBody AccountPostRequest request){
	    	
	    	try {
	    		//verificar se o login informado existe no banco de dados
	    		if(usuarioRepository.findByLogin(request.getLogin()) != null) {
	    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O login informado ja esta cadastrado no sistema, tente outro");
	    		}
	    		
	    		//cadastrando o usuario
	    		Usuario usuario = new Usuario();
	    		
	    		usuario.setNome(request.getNome());
	    		usuario.setLogin(request.getLogin());
	    		usuario.setSenha(MD5Cryptography.encrypt(request.getSenha()));
	    		
	    		usuarioRepository.save(usuario);
	    		
	    		return ResponseEntity.status(HttpStatus.OK).body("Conta de usuario criada com sucesso");
	    		
	    	}catch(Exception e) {
	    		
	    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    	}
	    	
			
		}

}
