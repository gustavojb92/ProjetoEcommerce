package org.serratec.backend.projetofinal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.serratec.backend.projetofinal.domain.Categoria;
import org.serratec.backend.projetofinal.domain.Produto;
import org.serratec.backend.projetofinal.dto.ProdutoCadastroDTO;
import org.serratec.backend.projetofinal.dto.ProdutoExibirDTO;
import org.serratec.backend.projetofinal.exceptions.CadastroExisteException;
import org.serratec.backend.projetofinal.repository.ProdutoRepository;
import org.serratec.backend.projetofinal.service.CategoriaService;
import org.serratec.backend.projetofinal.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaService categoriaService;

	@Override
	public List<ProdutoExibirDTO> pesquisarTodosProdutos() {

		List<Produto> produtos = produtoRepository.findAll();
		List<ProdutoExibirDTO> produtosDTO = new ArrayList<ProdutoExibirDTO>();

		for (Produto produto : produtos) {

			ProdutoExibirDTO produtoDTO = new ProdutoExibirDTO(produto);
			produtosDTO.add(produtoDTO);

		}
		return produtosDTO;
	}

	@Override
	public ProdutoExibirDTO pesquisarUmProduto(Long id) {

		ProdutoExibirDTO produtoDTO = new ProdutoExibirDTO();
		produtoDTO.setNome(produtoRepository.findById(id).get().getNome());
		produtoDTO.setCategoria(produtoRepository.findById(id).get().getCategoria());
		produtoDTO.setId(produtoRepository.findById(id).get().getId());
		produtoDTO.setQuantidade(produtoRepository.findById(id).get().getQuantidade());
		produtoDTO.setPrecoCompra(produtoRepository.findById(id).get().getPrecoCompra());

		return produtoDTO;

	}

	@Override
	public ProdutoExibirDTO inserirProduto(ProdutoCadastroDTO produtoCadastro) {

		Produto produtoTeste = produtoRepository.findByNome(produtoCadastro.getNome());

		if (produtoTeste != null) {

			throw new CadastroExisteException("Produto já cadastrado.");
		}

		Produto produto = new Produto();
		produto.setNome(produtoCadastro.getNome());
		String pegaNome = produtoCadastro.getNomeCategoria();
		Categoria categoriaNova = categoriaService.confereCategoriaPorNome(pegaNome);
		produto.setCategoria(categoriaNova);
		produto.setPrecoCompra(produtoCadastro.getPrecoCompra());
		produto.setValidade(produtoCadastro.getValidade());
		produto.setQuantidade(produtoCadastro.getQuantidade());

		produto = produtoRepository.save(produto);

		return new ProdutoExibirDTO(produto);
	}

	@Override
	public boolean existeProduto(Long id) {

		return produtoRepository.existsById(id);
	}

	@Override
	public String removerProduto(Long id) {

		produtoRepository.deleteById(id);

		return "Produto Deletado!";
	}

	@Override
	public ProdutoExibirDTO atualizarProduto(ProdutoCadastroDTO produtoCadastro, Long id) {

		Produto produtoTeste = produtoRepository.findByNome(produtoCadastro.getNome());

		Produto produto = produtoRepository.getById(id);

		produto.setNome(produtoCadastro.getNome());
		produto.setCategoria(categoriaService.confereCategoriaPorNome(produtoCadastro.getNomeCategoria()));
		produto.setPrecoCompra(produtoCadastro.getPrecoCompra());
		produto.setValidade(produtoCadastro.getValidade());
		produto.setQuantidade(produtoCadastro.getQuantidade());

		produto = produtoRepository.save(produto);

		return new ProdutoExibirDTO(produto);

	}

}
