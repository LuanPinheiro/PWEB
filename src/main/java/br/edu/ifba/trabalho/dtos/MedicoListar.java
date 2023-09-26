package br.edu.ifba.trabalho.dtos;

import br.edu.ifba.trabalho.models.Especialidade;
import br.edu.ifba.trabalho.models.Medico;

public record MedicoListar(String nome, String email, String crm, Especialidade especialidade) {

	public MedicoListar(Medico medico) {
		this(medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
	}
}
