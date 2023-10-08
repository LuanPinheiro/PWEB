package br.edu.ifba.pacientes.dtos;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.Valid;

public record PacienteAtualizar(
		String nome,
		String telefone,
		String email,
		@Valid EnderecoDTO endereco,
		@CPF String cpf) {
	
	private PacienteAtualizar() {
		this(null, null, null, null, null);
	}
	
	public boolean allFieldsNull() {
		if(this.equals(new PacienteAtualizar(null, null, null, null, null))){
			return true;
		}
		return false;
	}
}
