package br.edu.ifba.pacientes.dtos;

import br.edu.ifba.pacientes.models.Paciente;

public record PacienteListar(
		String nome,
		String email,
		String cpf) {

	public PacienteListar(Paciente paciente) {
		this(paciente.getDadosPessoais().getNome(), paciente.getDadosPessoais().getEmail(), paciente.getCpf());
	}
}
