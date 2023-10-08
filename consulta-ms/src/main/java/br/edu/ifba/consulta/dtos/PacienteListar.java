package br.edu.ifba.consulta.dtos;

import br.edu.ifba.consulta.models.Paciente;

public record PacienteListar(
		String nome,
		String email,
		String cpf) {

	public PacienteListar(Paciente paciente) {
		this(paciente.getDadosPessoais().getNome(), paciente.getDadosPessoais().getEmail(), paciente.getCpf());
	}
}
