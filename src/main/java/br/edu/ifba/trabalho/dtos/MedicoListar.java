package br.edu.ifba.trabalho.dtos;

import br.edu.ifba.trabalho.models.Especialidade;
import br.edu.ifba.trabalho.models.Medico;
import jakarta.validation.constraints.NotBlank;

public record MedicoListar(String nome, String email, String crm, Especialidade especialidade) {

	public MedicoListar(Medico medico) {
		this(medico.getDadosPessoais().getNome(), medico.getDadosPessoais().getEmail(), medico.getCrm(), medico.getEspecialidade());
	}
}
