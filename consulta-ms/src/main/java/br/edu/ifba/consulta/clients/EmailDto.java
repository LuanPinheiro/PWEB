package br.edu.ifba.consulta.clients;

public record EmailDto(
		String mailFrom,
		String mailTo,
		String mailSubject,
		String mailText) {
}
