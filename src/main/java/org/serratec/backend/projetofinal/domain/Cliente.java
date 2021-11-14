package org.serratec.backend.projetofinal.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	@ApiModelProperty(value = "Identificador único do Cliente")
	private Long id;

	@NotBlank(message = "O nome do cliente não pode estar vazio")
	@Column(name = "Nome", nullable = false)
	@Size(max = 60, message = "O nome da categoria não deve ultrapassar 60 caracteres.")
	@ApiModelProperty(value = "Nome do cliente acima de 60 caracteteres")
	private String nome;

	@Size(max = 11)
	@Size(min = 11)
	@NotBlank(message = "O cpf não pode ser nulo ou vazio.")
	@CPF(message = "Digite um CPF válido.")
	@Column(name = "Cpf", nullable = false)
	@ApiModelProperty(value = "CPF", required = true)
	private String cpf;

	@Size(max = 50)
	@Email(message = "Email Inválido!")
	@NotBlank(message = "O email não pode ser nulo ou vazio.")
	@Column(name = "Email", nullable = false, length = 50)
	@ApiModelProperty(value = "Email", required = true)
	private String email;

	
	@Column(name = "Nascimento")
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "Data de Nascimento", required = true)
	private Date dataNascimento;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_endereco")
	private Endereco endereco;

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String toString() {

		return "Código: " + this.id + "\n Nome: " + this.nome + "\n Email: " + this.email;
	}

}
