package org.serratec.backend.projetofinal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.serratec.backend.projetofinal.configurations.MailConfig;
import org.serratec.backend.projetofinal.domain.Cliente;
import org.serratec.backend.projetofinal.dto.ClienteCadastroDTO;
import org.serratec.backend.projetofinal.dto.ClienteExibirDTO;
import org.serratec.backend.projetofinal.exceptions.CpfException;
import org.serratec.backend.projetofinal.exceptions.EmailException;
import org.serratec.backend.projetofinal.repository.ClienteRepository;
import org.serratec.backend.projetofinal.service.ClienteService;
import org.serratec.backend.projetofinal.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private MailConfig mailConfig;

	@Override
	public List<ClienteExibirDTO> pesquisarTodosClientes() {

		List<Cliente> clientes = clienteRepository.findAll();
		List<ClienteExibirDTO> clientesDTO = new ArrayList<ClienteExibirDTO>();

		for (Cliente cliente : clientes) {

			ClienteExibirDTO clienteDTO = new ClienteExibirDTO(cliente);
			clientesDTO.add(clienteDTO);

		}

		return clientesDTO;
	}

	@Override
	public ClienteExibirDTO inserirCliente(ClienteCadastroDTO clienteCadastroDTO) throws EmailException, CpfException {

		Cliente clienteTeste = clienteRepository.findByEmail(clienteCadastroDTO.getEmail());

		if (clienteTeste != null) {

			throw new EmailException("Email já existente no banco de dados.");
		}

		clienteTeste = clienteRepository.findByCpf(clienteCadastroDTO.getCpf());

		if (clienteTeste != null) {

			throw new CpfException("CPF já existente no banco de dados.");
		}

		Cliente cliente = new Cliente();
		cliente.setNome(clienteCadastroDTO.getNome());
		cliente.setEmail(clienteCadastroDTO.getEmail());
		cliente.setCpf(clienteCadastroDTO.getCpf());
		cliente.setDataNascimento(clienteCadastroDTO.getDataNascimento());
		cliente.setEndereco(enderecoService.buscarCep(clienteCadastroDTO.getCep()));
		cliente = clienteRepository.save(cliente);

		mailConfig.sendEmail(cliente.getEmail(), "Cadastro do usuário", cliente.toString());

		return new ClienteExibirDTO(cliente);

	}

	@Override
	public boolean existeCliente(Long id) {

		return clienteRepository.existsById(id);
	}

	@Override
	public String removerCliente(Long id) {

		clienteRepository.deleteById(id);

		return "Cliente Deletado!";

	}

	@Override
	public ClienteExibirDTO pesquisarUmCliente(Long id) {

		ClienteExibirDTO clienteDTO = new ClienteExibirDTO();
		clienteDTO.setNome(clienteRepository.findById(id).get().getNome());
		clienteDTO.setEmail(clienteRepository.findById(id).get().getEmail());
		clienteDTO.setId(clienteRepository.findById(id).get().getId());
		clienteDTO.setEndereco(clienteRepository.findById(id).get().getEndereco());
		return clienteDTO;

	}

	@Override
	public ClienteExibirDTO atualizarCliente(ClienteCadastroDTO clienteCadastroDTO, Long id)
			throws EmailException, CpfException {

		Cliente cliente = clienteRepository.getById(id);

		cliente.setNome(clienteCadastroDTO.getNome());
		cliente.setEmail(clienteCadastroDTO.getEmail());
		cliente.setCpf(clienteCadastroDTO.getCpf());
		cliente.setDataNascimento(clienteCadastroDTO.getDataNascimento());
		cliente = clienteRepository.save(cliente);

		mailConfig.sendEmail(cliente.getEmail(), "Cadastro do usuário atualizado!", cliente.toString());

		return new ClienteExibirDTO(cliente);
	}

}
