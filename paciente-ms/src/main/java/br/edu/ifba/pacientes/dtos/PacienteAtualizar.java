package br.edu.ifba.pacientes.dtos;

import org.hibernate.validator.constraints.br.CPF;

import br.edu.ifba.pacientes.clients.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record PacienteAtualizar(
		String nome,
		String telefone,
		String email,
		@Valid EnderecoDTO endereco,
		@NotBlank(message = "Por favor indique o CPF do paciente que será atualizado") @CPF String cpf) {
	
	public boolean allFieldsNull() {
		if(this.equals(new PacienteAtualizar(null, null, null, null, null))){
			return true;
		}
		return false;
	}
}
