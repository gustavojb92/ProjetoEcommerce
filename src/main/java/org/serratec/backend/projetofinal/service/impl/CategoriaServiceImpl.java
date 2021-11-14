package org.serratec.backend.projetofinal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.serratec.backend.projetofinal.domain.Categoria;
import org.serratec.backend.projetofinal.dto.CategoriaCadastroDTO;
import org.serratec.backend.projetofinal.dto.CategoriaExibirDTO;
import org.serratec.backend.projetofinal.dto.ClienteExibirDTO;
import org.serratec.backend.projetofinal.exceptions.CadastroExisteException;
import org.serratec.backend.projetofinal.repository.CategoriaRepository;
import org.serratec.backend.projetofinal.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Override
	public boolean existeCategoria(Long id) {
		return categoriaRepository.existsById(id);
	}

	@Override
	public String removerCategoria(Long id) {
		categoriaRepository.deleteById(id);

		return "Categoria Deletada!";
	}

	@Override
	public CategoriaExibirDTO inserirCategoria(CategoriaCadastroDTO categoriaCadastro) {
		Categoria categoriaTeste = categoriaRepository.findByNome(categoriaCadastro.getNome());

		if (categoriaTeste != null) {

			throw new CadastroExisteException("Categoria já cadastrada.");
		}

		Categoria categoria = new Categoria();
		categoria.setNome(categoriaCadastro.getNome());
		categoria.setDescricao(categoriaCadastro.getDescricao());
		categoria = categoriaRepository.save(categoria);

		return new CategoriaExibirDTO(categoria);
	}

	@Override
	public CategoriaExibirDTO pesquisarUmaCategoria(Long id) {

		CategoriaExibirDTO categoriaDTO = new CategoriaExibirDTO();
		categoriaDTO.setNome(categoriaRepository.findById(id).get().getNome());
		categoriaDTO.setDescricao(categoriaRepository.findById(id).get().getDescricao());
		categoriaDTO.setId(categoriaRepository.findById(id).get().getId());

		return categoriaDTO;
	}

	@Override
	public CategoriaExibirDTO atualizarCategoria(CategoriaCadastroDTO categoriaCadastro, Long id) {

		Categoria categoria = categoriaRepository.getById(id);

		categoria.setNome(categoriaCadastro.getNome());
		categoria.setDescricao(categoriaCadastro.getDescricao());

		categoria = categoriaRepository.save(categoria);

		return new CategoriaExibirDTO(categoria);
	}

	@Override
	public List<CategoriaExibirDTO> pesquisarTodasCategorias() {

		List<Categoria> categorias = categoriaRepository.findAll();
		List<CategoriaExibirDTO> categoriasDTO = new ArrayList<CategoriaExibirDTO>();

		for (Categoria categoria : categorias) {

			CategoriaExibirDTO categoriaDTO = new CategoriaExibirDTO(categoria);
			categoriasDTO.add(categoriaDTO);

		}

		return categoriasDTO;

	}

	@Override
	public Categoria confereCategoriaPorNome(String nomeCategoria) {

		if (categoriaRepository.existsByNome(nomeCategoria)) {

			Categoria categoria = new Categoria();
			categoria = categoriaRepository.findByNome(nomeCategoria);

			return categoria;

		} else {
			return null;
		}

	}

}
