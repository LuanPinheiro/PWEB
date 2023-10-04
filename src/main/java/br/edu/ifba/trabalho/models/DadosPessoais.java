package br.edu.ifba.trabalho.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class DadosPessoais {
	
	@Column(nullable = false)
	@NotBlank(message = "Nome não pode ser vazio")
	private String nome;
	
	@Column(nullable = false)
	@NotBlank(message = "Email não pode ser vazio")
	private String email;
	
	@Column(nullable = false)
	@NotBlank(message = "Telefone não pode ser vazio")
	private String telefone;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(nullable = false)
	@Valid
	private Endereco endereco;
	
	public DadosPessoais() {
		
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
