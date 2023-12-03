package br.edu.ifba.consulta.amqp;

import br.edu.ifba.consulta.models.Motivo;

public record DesativacaoDTO(
		String id,
		Motivo motivo) {
}
