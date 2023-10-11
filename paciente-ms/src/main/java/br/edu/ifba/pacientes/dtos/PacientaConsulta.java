package br.edu.ifba.pacientes.dtos;

import br.edu.ifba.pacientes.models.Paciente;

public record PacientaConsulta(Long id, String email, String nome) {

	public PacientaConsulta(Paciente paciente) {
		this(
				paciente.getId(),
				paciente.getDadosPessoais().getEmail(),
				paciente.getDadosPessoais().getNome());
	}
}
