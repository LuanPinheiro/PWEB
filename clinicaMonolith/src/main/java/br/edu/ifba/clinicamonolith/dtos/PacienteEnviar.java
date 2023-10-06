package br.edu.ifba.clinicamonolith.dtos;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.Valid;

public record PacienteEnviar(
		@Valid DadosPessoaisDTO dadosPessoais,
		@CPF String cpf) {
}
