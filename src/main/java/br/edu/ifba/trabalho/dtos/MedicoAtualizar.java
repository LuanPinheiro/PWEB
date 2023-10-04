package br.edu.ifba.trabalho.dtos;

import br.edu.ifba.trabalho.models.Especialidade;
import jakarta.validation.Valid;

public record MedicoAtualizar(
		String nome,
		String telefone,
		String email,
		@Valid EnderecoEnviar endereco,
		String crm,
		Especialidade especialidade) {

	private MedicoAtualizar() {
		this(null, null, null, null, null, null);
	}
	
	public boolean allFieldsNull() {
		if(this.equals(new MedicoAtualizar(null, null, null, null, null, null))){
			return true;
		}
		return false;
	}

}
