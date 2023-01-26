package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.repositories.IProdutoRepositoryy;
import br.com.cotiinformatica.responses.ProdutoGetResponse;
import br.com.cotiinnformatica.requests.ProdutoPostRequest;
import br.com.cotiinnformatica.requests.ProdutoPutRequest;
import jakarta.transaction.Transactional;

@Controller
@Transactional
public class ProdutoController {

	@Autowired
	private IProdutoRepositoryy produtoRepository;

	// definindo o endereco do servico
	private static final String ENDPOINT = "/api/produtos";

	// metodo para realizar o servico de cadastro de produto
	@PostMapping(ENDPOINT)
	public ResponseEntity<String> post(@RequestBody ProdutoPostRequest request) {

		try {
			Produto produto = new Produto();

			produto.setNome(request.getNome());
			produto.setPreco(request.getPreco());
			produto.setQuantidade(request.getQuantidade());
			produto.setDescricao(request.getDescricao());

			produtoRepository.save(produto);

			return ResponseEntity.status(HttpStatus.OK).body("Produto cadastrado com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro:" + e.getMessage());
		}

	}

	// metodo para realizar o servico de edicao do produto
	@PutMapping(ENDPOINT)
	public ResponseEntity<String> put(@RequestBody ProdutoPutRequest request) {

		try {
			// consultar o produto no banco de dados atraves do ID
			Optional<Produto> item = produtoRepository.findById(request.getIdProduto());

			// verificar se o produto nao foi encontrado
			if (item.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Produto nao encontrado, por favor verifique.");
			} else {
				Produto produto = item.get();

				produto.setNome(request.getNome());
				produto.setPreco(request.getPreco());
				produto.setQuantidade(request.getQuantidade());
				produto.setDescricao(request.getDescricao());

				produtoRepository.save(produto);

				return ResponseEntity.status(HttpStatus.OK).body("Produto atualizado com sucesso.");
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro:" + e.getMessage());

		}

	}

	// metodo para realizar o servico de exclusao do produto
	@DeleteMapping(ENDPOINT + "/{idProduto}")
	public ResponseEntity<String> delete(@PathVariable("idProduto") Integer idProduto) {

		try {
			// consultar o produto no banco de dados atraves do id
			Optional<Produto> item = produtoRepository.findById(idProduto);

			// verificar se o produto nao foi encontrado
			if (item.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Produto nao encontrado, por favor verifique.");
			}else {
				Produto produto = item.get();
				produtoRepository.delete(produto);
				
				return ResponseEntity.status(HttpStatus.OK).body("Produto excluido com sucesso.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro:" + e.getMessage());
		}

	}

	// metodo para realizar o servico de consulta do produto
	@GetMapping(ENDPOINT)

	public ResponseEntity<List<ProdutoGetResponse>> get() {
		
		List<ProdutoGetResponse> response = new ArrayList<ProdutoGetResponse>();
		
		for(Produto produto : produtoRepository.findAll()) {
			ProdutoGetResponse item = new ProdutoGetResponse();
			
			item.setIdProduto(produto.getIdProduto());
			item.setNome(produto.getNome());
			item.setDescricao(produto.getDescricao());
			item.setPreco(produto.getPreco());
			item.setQuantidade(produto.getQuantidade());
			item.setTotal(produto.getPreco() * produto.getQuantidade());
			
			response.add(item);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	//Metodo para consultar 1 produto baseado no ID
	@GetMapping(ENDPOINT + "/{idProduto}")
	public ResponseEntity<ProdutoGetResponse>getById(@PathVariable("idProduto")Integer idProduto){
		
		//consultar o produto no banco de dados atraves do ID
		Optional<Produto> item = produtoRepository.findById(idProduto);
		
		//verificar se o produto nao foi encontrado
		if(item.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}else {
			ProdutoGetResponse response = new ProdutoGetResponse();
			Produto produto = item.get();
			
			response.setIdProduto(produto.getIdProduto());
			response.setNome(produto.getNome());
			response.setDescricao(produto.getDescricao());
			response.setPreco(produto.getPreco());
			response.setQuantidade(produto.getQuantidade());
			response.setTotal(produto.getPreco() * produto.getQuantidade());
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
}
