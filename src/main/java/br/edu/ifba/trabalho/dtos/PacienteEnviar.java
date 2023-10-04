package br.edu.ifba.trabalho.dtos;

import org.hibernate.validator.constraints.br.CPF;

import br.edu.ifba.trabalho.models.DadosPessoais;
import jakarta.validation.Valid;

public record PacienteEnviar(
		@Valid DadosPessoais dadosPessoais,
		@CPF String cpf,
		@Valid EnderecoEnviar endereco) {
}
