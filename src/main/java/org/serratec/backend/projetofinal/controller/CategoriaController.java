package org.serratec.backend.projetofinal.controller;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.serratec.backend.projetofinal.dto.CategoriaCadastroDTO;
import org.serratec.backend.projetofinal.dto.CategoriaExibirDTO;
import org.serratec.backend.projetofinal.exceptions.CadastroExisteException;
import org.serratec.backend.projetofinal.service.CategoriaService;
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
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	CategoriaService categoriaService;

	@GetMapping
	@ApiOperation(value = "Listar todas as Categorias", notes = "Listar todas as Categorias")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna todas as Categorias"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro interno do servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção") })

	public ResponseEntity<List<CategoriaExibirDTO>> pesquisarTodasCategorias() {

		List<CategoriaExibirDTO> listaCategorias = categoriaService.pesquisarTodasCategorias();

		return ResponseEntity.ok(listaCategorias);

	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Listar uma Categoria", notes = "Listar uma Categoria")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma Categoria"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro interno do servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção") })

	public ResponseEntity<CategoriaExibirDTO> pesquisarUmaCategoria(@PathVariable Long id) {

		CategoriaExibirDTO categoriaDTO = categoriaService.pesquisarUmaCategoria(id);

		if (categoriaService.existeCategoria(id)) {

			return ResponseEntity.ok(categoriaDTO);

		} else {

			return ResponseEntity.notFound().build();

		}

	}

	@PostMapping
	@ApiOperation(value = "Inserir uma Categoria", notes = "Inserir uma Categoria")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Categoria adicionada"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro interno do servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção") })

	public ResponseEntity<Object> inserir(@Valid @RequestBody CategoriaCadastroDTO categoriaCadastro) {

		try {

			CategoriaExibirDTO categoriaDTO = categoriaService.inserirCategoria(categoriaCadastro);

			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(categoriaDTO.getId()).toUri();

			return ResponseEntity.created(uri).body(categoriaDTO);
		} catch (CadastroExisteException e) {
			return ResponseEntity.unprocessableEntity().body(e.getMessage());

		}
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar uma Categoria", notes = "Atualizar uma Categoria")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Categoria atualizada"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro interno do servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção") })

	public ResponseEntity<CategoriaExibirDTO> atualizar(@RequestBody CategoriaCadastroDTO categoriaCadastro,
			@PathVariable Long id) {

		if (!categoriaService.existeCategoria(id)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(categoriaService.atualizarCategoria(categoriaCadastro, id));

	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = " Remover uma Categoria", notes = "Remover uma Categoria")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Categoria removida"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro interno do servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção") })
	public ResponseEntity<String> remover(@PathVariable Long id) {

		if (!categoriaService.existeCategoria(id)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(categoriaService.removerCategoria(id));
	}

}
