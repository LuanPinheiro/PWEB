package br.edu.ifba.endereco.models;

import br.edu.ifba.endereco.dtos.EnderecoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "enderecos")
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String logradouro;
	
	private String numero;
	private String complemento;
	
	@Column(nullable = false)
	private String bairro;
	
	@Column(nullable = false)
	private String cidade;
	
	@Column(nullable = false)
	private String uf;
	
	@Column(nullable = false)
	private String cep;
	
	public Endereco() {
		
	}
	
	public Endereco (EnderecoDTO endereco) {
		this.bairro = endereco.bairro();
		this.cep = endereco.cep();
		this.cidade = endereco.cidade();
		this.complemento = endereco.complemento();
		this.logradouro = endereco.logradouro();
		this.numero = endereco.numero();
		this.uf = endereco.uf();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
     * Retorna "true" caso os valores do DTO sejam iguais aos valores do registro
     */
	public boolean equalsDtoValues(EnderecoDTO endereco) {
		return (bairro == endereco.bairro()) && (cep == endereco.cep())
				&& (cidade == endereco.cidade()) && (complemento == endereco.complemento())
				&& (logradouro == endereco.logradouro()) && (numero == endereco.numero())
				&& (uf == endereco.uf());
	}
	
	
}
