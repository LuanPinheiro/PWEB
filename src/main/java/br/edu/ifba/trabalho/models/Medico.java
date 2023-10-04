package br.edu.ifba.trabalho.models;

import org.hibernate.annotations.ColumnDefault;

import br.edu.ifba.trabalho.dtos.MedicoEnviar;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;

@Entity(name = "medicos")
public class Medico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	@Valid
	private DadosPessoais dadosPessoais;
	
	@Column(unique = true, nullable = false)
	private String crm;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Especialidade especialidade;
	
	@ColumnDefault(value = "TRUE")
	private Boolean ativo;
	
	public Medico() {
		
	}
	
	public Medico(MedicoEnviar dados) {
		this.dadosPessoais = dados.dadosPessoais();
		this.crm = dados.crm();
		this.especialidade = dados.especialidade();
	}
	
	public Long getId() {
		return id;
	}
	public DadosPessoais getDadosPessoais() {
		return dadosPessoais;
	}
	public void setDadosPessoais(DadosPessoais dadosPessoais) {
		this.dadosPessoais = dadosPessoais;
	}
	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}
	public Especialidade getEspecialidade() {
		return especialidade;
	}
	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}
