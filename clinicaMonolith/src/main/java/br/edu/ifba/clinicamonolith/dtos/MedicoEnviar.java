package br.edu.ifba.clinicamonolith.dtos;

import br.edu.ifba.clinicamonolith.models.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record MedicoEnviar(
		@Valid DadosPessoaisDTO dadosPessoais,
		@NotBlank(message = "CRM não pode ser enviado vazio") String crm,
		Especialidade especialidade) {
}
