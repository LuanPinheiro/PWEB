package br.edu.ifba.trabalho.dtos;

import jakarta.validation.Valid;

public record PacienteAtualizar(
		String nome,
		String telefone,
		@Valid EnderecoEnviar endereco,
		String cpf) {
	
	private PacienteAtualizar() {
		this(null, null, null, null);
	}
	
	public boolean allFieldsNull() {
		if(this.equals(new PacienteAtualizar(null, null, null, null))){
			return true;
		}
		return false;
	}
}
