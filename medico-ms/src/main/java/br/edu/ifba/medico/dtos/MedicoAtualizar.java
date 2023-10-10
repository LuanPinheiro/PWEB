package br.edu.ifba.medico.dtos;

import br.edu.ifba.medico.clients.EnderecoDTO;
import br.edu.ifba.medico.models.Especialidade;
import jakarta.validation.Valid;

public record MedicoAtualizar(
		String nome,
		String telefone,
		String email,
		@Valid EnderecoDTO endereco,
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
