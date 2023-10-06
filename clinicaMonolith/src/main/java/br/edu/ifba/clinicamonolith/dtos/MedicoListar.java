package br.edu.ifba.clinicamonolith.dtos;

import br.edu.ifba.clinicamonolith.models.Especialidade;
import br.edu.ifba.clinicamonolith.models.Medico;

public record MedicoListar(
		String nome,
		String email,
		String crm,
		Especialidade especialidade) {

	public MedicoListar(Medico medico) {
		this(medico.getDadosPessoais().getNome(), medico.getDadosPessoais().getEmail(), medico.getCrm(), medico.getEspecialidade());
	}
}
