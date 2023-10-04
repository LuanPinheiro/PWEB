package br.edu.ifba.trabalho.dtos;

import br.edu.ifba.trabalho.models.Paciente;

public record PacienteListar(
		String nome,
		String email,
		String cpf) {

	public PacienteListar(Paciente paciente) {
		this(paciente.getDadosPessoais().getNome(), paciente.getDadosPessoais().getEmail(), paciente.getCpf());
	}
}
