package br.edu.ifba.trabalho.models;

import java.util.Objects;

import br.edu.ifba.trabalho.dtos.EnderecoEnviar;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity(name = "enderecos")
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotBlank(message = "Logradouro não pode ser vazio")
	private String logradouro;
	
	private String numero;
	private String complemento;
	
	@Column(nullable = false)
	@NotBlank(message = "Bairro não pode ser vazio")
	private String bairro;
	
	@Column(nullable = false)
	@NotBlank(message = "Cidade não pode ser vazio")
	private String cidade;
	
	@Column(nullable = false)
	@NotBlank(message = "UF não pode ser vazio")
	private String uf;
	
	@Column(nullable = false)
	@NotBlank(message = "CEP não pode ser vazio")
	private String cep;
	
	public Endereco() {
		
	}
	
	public Endereco (EnderecoEnviar endereco) {
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		return Objects.equals(bairro, other.bairro) && Objects.equals(cep, other.cep)
				&& Objects.equals(cidade, other.cidade) && Objects.equals(complemento, other.complemento)
				&& Objects.equals(logradouro, other.logradouro)
				&& Objects.equals(numero, other.numero) && Objects.equals(uf, other.uf);
	}
	
	
}
