package br.edu.ifba.pacientes.dtos;

import br.edu.ifba.pacientes.models.Paciente;

public record PacienteConsulta(Long id, String email, String nome) {

	public PacienteConsulta(Paciente paciente) {
		this(
				paciente.getId(),
				paciente.getDadosPessoais().getEmail(),
				paciente.getDadosPessoais().getNome());
	}
}
