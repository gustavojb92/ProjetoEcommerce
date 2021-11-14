package org.serratec.backend.projetofinal.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.serratec.backend.projetofinal.dto.ProdutoCadastroDTO;
import org.serratec.backend.projetofinal.dto.ProdutoExibirDTO;
import org.serratec.backend.projetofinal.exceptions.CadastroExisteException;
import org.serratec.backend.projetofinal.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping
	@ApiOperation(value = "Listar todos os Produtos", notes = "Listar todos os Produtos")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busca realizada com sucesso"),
			@ApiResponse(code = 201, message = "Pedido localizado com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso Indisponível"),
			@ApiResponse(code = 500, message = "Erros interno do servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção") })
	public ResponseEntity<List<ProdutoExibirDTO>> pesquisarTodosProdutos() {
		List<ProdutoExibirDTO> listaProdutos = produtoService.pesquisarTodosProdutos();

		return ResponseEntity.ok(listaProdutos);

	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Listar um Produto", notes = "Listar um Produto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busca realizada com sucesso"),
			@ApiResponse(code = 201, message = "Pedido localizado com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso Indisponível"),
			@ApiResponse(code = 500, message = "Erros interno do servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção") })
	public ResponseEntity<ProdutoExibirDTO> pesquisarUmProduto(@PathVariable Long id) {
		ProdutoExibirDTO produtoDTO = produtoService.pesquisarUmProduto(id);

		if (produtoService.existeProduto(id)) {
			return ResponseEntity.ok(produtoDTO);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PostMapping
	@ApiOperation(value = "Inserir um Produto", notes = "Inserir um Produto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busca realizada com sucesso"),
			@ApiResponse(code = 201, message = "Pedido localizado com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso Indisponível"),
			@ApiResponse(code = 500, message = "Erros interno do servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção") })
	public ResponseEntity<Object> inserir(@Valid @RequestBody ProdutoCadastroDTO produtoCadastroDTO) {

		try {

			ProdutoExibirDTO produtoDTO = produtoService.inserirProduto(produtoCadastroDTO);

			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produtoDTO.getId())
					.toUri();

			return ResponseEntity.created(uri).body(produtoDTO);

		} catch (CadastroExisteException e) {
			return ResponseEntity.unprocessableEntity().body(e.getMessage());

		}
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar um Produto", notes = "Atualizar um Produto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busca realizada com sucesso"),
			@ApiResponse(code = 201, message = "Pedido localizado com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso Indisponível"),
			@ApiResponse(code = 500, message = "Erros interno do servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção") })
	public ResponseEntity<ProdutoExibirDTO> atualizar(@RequestBody ProdutoCadastroDTO produtoCadastro,
			@PathVariable Long id) {

		if (!produtoService.existeProduto(id)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(produtoService.atualizarProduto(produtoCadastro, id));

	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Remover um Produto", notes = "Remover um Produto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busca realizada com sucesso"),
			@ApiResponse(code = 201, message = "Pedido localizado com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso Indisponível"),
			@ApiResponse(code = 500, message = "Erros interno do servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção") })
	public ResponseEntity<String> remover(@PathVariable Long id) {
		if (!produtoService.existeProduto(id)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(produtoService.removerProduto(id));
	}

}
