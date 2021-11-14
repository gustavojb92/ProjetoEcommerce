package org.serratec.backend.projetofinal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.serratec.backend.projetofinal.domain.ItemPedido;
import org.serratec.backend.projetofinal.domain.Produto;
import org.serratec.backend.projetofinal.dto.ItemPedidoCadastroDTO;
import org.serratec.backend.projetofinal.dto.ItemPedidoExibirDTO;
import org.serratec.backend.projetofinal.exceptions.EmailException;
import org.serratec.backend.projetofinal.exceptions.EstoqueException;
import org.serratec.backend.projetofinal.repository.ItemPedidoRepository;
import org.serratec.backend.projetofinal.repository.ProdutoRepository;
import org.serratec.backend.projetofinal.service.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService {

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public List<ItemPedidoExibirDTO> pesquisarTodosItensPedidos() {

		List<ItemPedido> itensPedidos = itemPedidoRepository.findAll();
		List<ItemPedidoExibirDTO> itensPedidosDTO = new ArrayList<ItemPedidoExibirDTO>();

		for (ItemPedido itemPedido : itensPedidos) {

			ItemPedidoExibirDTO itemPedidoDTO = new ItemPedidoExibirDTO(itemPedido);
			itensPedidosDTO.add(itemPedidoDTO);

		}

		return itensPedidosDTO;
	}

	@Override
	public ItemPedidoExibirDTO inserirItemPedido(ItemPedidoCadastroDTO itemPedidoCadastroDTO) throws EstoqueException {
		ItemPedido itemTeste = new ItemPedido();

		itemTeste.setQuantidade(itemPedidoCadastroDTO.getQuantidade());
		itemTeste.setProduto(produtoRepository.findById(itemPedidoCadastroDTO.getIdProduto()).get());

		if (itemTeste.getQuantidade() > itemTeste.getProduto().getQuantidade()) {
			throw new EstoqueException("Estoque Insuficinte");
		}

		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setPrecoVenda(itemPedidoCadastroDTO.getPrecoVenda());
		itemPedido.setQuantidade(itemPedidoCadastroDTO.getQuantidade());
		itemPedido.setitensTotal(itemPedido.getQuantidade() * itemPedido.getPrecoVenda());

		itemPedido.setProduto(produtoRepository.findById(itemPedidoCadastroDTO.getIdProduto()).get());

		itemPedido = itemPedidoRepository.save(itemPedido);

		return new ItemPedidoExibirDTO(itemPedido);

	}

	@Override
	public boolean existeItemPedido(Long id) {

		return itemPedidoRepository.existsById(id);
	}

	@Override
	public ItemPedidoExibirDTO pesquisarUmItemPedido(Long id) {
		return null;
	}

	@Override
	public ItemPedido findByQuantidade(Integer quantidade) {
		return null;
	}

}
