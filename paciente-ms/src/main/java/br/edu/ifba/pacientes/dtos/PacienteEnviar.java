package br.edu.ifba.pacientes.dtos;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record PacienteEnviar(
		@Valid DadosPessoaisDTO dadosPessoais,
		@NotBlank(message = "Campo CPF não pode ser vazio") @CPF String cpf) {
}
