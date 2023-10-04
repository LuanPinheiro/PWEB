package br.edu.ifba.trabalho.models;

import org.hibernate.annotations.ColumnDefault;

import br.edu.ifba.trabalho.dtos.PacienteEnviar;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;

@Entity(name = "pacientes")
public class Paciente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	@Valid
	private DadosPessoais dadosPessoais;
	
	@Column(unique = true, nullable = false)
	private String cpf;
	
	@ColumnDefault(value = "TRUE")
	private Boolean ativo;
	
	public Paciente() {
		
	}
	
	public Paciente(PacienteEnviar dados) {
		this.dadosPessoais = dados.dadosPessoais();
		this.cpf = dados.cpf();
	}

	public DadosPessoais getDadosPessoais() {
		return dadosPessoais;
	}

	public void setDadosPessoais(DadosPessoais dadosPessoais) {
		this.dadosPessoais = dadosPessoais;
	}
	public Long getId() {
		return id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}
