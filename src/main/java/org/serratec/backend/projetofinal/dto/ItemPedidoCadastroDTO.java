package org.serratec.backend.projetofinal.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import org.serratec.backend.projetofinal.domain.ItemPedido;

public class ItemPedidoCadastroDTO {

	
	@DecimalMin(value = "0.2", message = "Este valor não pode ser menor que 20 centavos!")
	private Double precoVenda;

	
	@DecimalMin(value = "1", message = "A quantidade não pode ser menor que 1!")
	private Integer quantidade;

	private Long idProduto;
	private Double itensTotal;

	public ItemPedidoCadastroDTO() {
	}

	public ItemPedidoCadastroDTO(ItemPedido itemPedido) {
		super();
		this.precoVenda = itemPedido.getPrecoVenda();
		this.quantidade = itemPedido.getQuantidade();
		this.idProduto = itemPedido.getProduto().getId();
		this.itensTotal = itemPedido.getitensTotal();

	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public Double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(Double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getItensTotal() {
		return itensTotal;
	}

	public void setItensTotal(Double itensTotal) {
		this.itensTotal = itensTotal;
	}

}
