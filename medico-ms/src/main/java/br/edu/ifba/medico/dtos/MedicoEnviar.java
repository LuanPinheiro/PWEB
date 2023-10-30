package br.edu.ifba.medico.dtos;

import br.edu.ifba.medico.models.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicoEnviar(
		@Valid DadosPessoaisDTO dadosPessoais,
		@NotBlank(message = "CRM não pode ser enviado vazio") String crm,
		@NotNull(message = "Especialidade não pode ser enviado vazio") Especialidade especialidade) {
}
