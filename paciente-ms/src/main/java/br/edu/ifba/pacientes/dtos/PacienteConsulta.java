package br.edu.ifba.pacientes.dtos;

import br.edu.ifba.pacientes.models.Paciente;

public record PacienteConsulta(String cpf, String email, String nome) {

	public PacienteConsulta(Paciente paciente) {
		this(
				paciente.getCpf(),
				paciente.getDadosPessoais().getEmail(),
				paciente.getDadosPessoais().getNome());
	}
}
