package br.edu.ifba.consulta.clients;

public record MedicoListar(
		String nome,
		String email,
		String crm,
		Especialidade especialidade) {
}
