package br.edu.ifba.medico.dtos;

import br.edu.ifba.medico.models.Especialidade;
import br.edu.ifba.medico.models.Medico;

public record MedicoConsulta(Long id, String nome, Especialidade especialidade) {
	public MedicoConsulta(Medico medico) {
		this(
				medico.getId(),
				medico.getDadosPessoais().getNome(),
				medico.getEspecialidade()
		);
	}
}
