package br.edu.ifba.consulta.amqp;

public record EmailDto(
		String mailFrom,
		String mailTo,
		String mailSubject,
		String mailText) {
}
