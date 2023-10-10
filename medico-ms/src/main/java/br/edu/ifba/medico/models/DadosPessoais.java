package br.edu.ifba.medico.models;

import br.edu.ifba.medico.dtos.DadosPessoaisDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;

@Embeddable
public class DadosPessoais {
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String telefone;
	
	@Column(nullable = false, name = "endereco_id")
	private Long endereco;
	
	public DadosPessoais() {
		
	}
	
	public DadosPessoais(DadosPessoaisDTO dados, Long endereco) {
		this.nome = dados.nome();
		this.email = dados.email();
		this.telefone = dados.telefone();
		this.endereco = endereco;
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

	public Long getEndereco() {
		return endereco;
	}
	public void setEndereco(Long endereco) {
		this.endereco = endereco;
	}
}
