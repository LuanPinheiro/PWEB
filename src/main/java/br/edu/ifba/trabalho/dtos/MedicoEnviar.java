package br.edu.ifba.trabalho.dtos;

import br.edu.ifba.trabalho.models.DadosPessoais;
import br.edu.ifba.trabalho.models.Especialidade;

public record MedicoEnviar(DadosPessoais dadosPessoais, String crm, Especialidade especialidade, EnderecoEnviar endereco) {

	public MedicoEnviar() {
		this(null, null, null, null);
	}
}
