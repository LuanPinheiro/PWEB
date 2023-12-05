package br.edu.ifba.pacientes.models;

import org.hibernate.annotations.ColumnDefault;

import br.edu.ifba.pacientes.dtos.PacienteEnviar;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "pacientes")
public class Paciente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	private DadosPessoais dadosPessoais;
	
	@Column(unique = true, nullable = false)
	private String cpf;
	
	@ColumnDefault(value = "TRUE")
	private Boolean ativo;
	
	public Paciente() {
		this.ativo = false;
	}
	
	public Paciente(PacienteEnviar dados, Long endereco) {
		this.dadosPessoais = new DadosPessoais(dados.dadosPessoais(), endereco);
		this.cpf = dados.cpf();
		this.ativo = false;
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
	public Boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "Paciente [id=" + id + ", dadosPessoais=" + dadosPessoais + ", cpf=" + cpf + ", ativo=" + ativo + "]";
	}
	
	
}
