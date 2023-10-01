package br.edu.ifba.trabalho.dtos;

import br.edu.ifba.trabalho.models.Especialidade;

public record MedicoEnviar(String nome, String email, String telefone, String crm, Especialidade especialidade, EnderecoEnviar endereco) {

	public MedicoEnviar() {
		this(null, null, null, null, null, null);
	}
}
