package br.edu.ifba.trabalho.dtos;

import br.edu.ifba.trabalho.models.DadosPessoais;
import br.edu.ifba.trabalho.models.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record MedicoEnviar(@Valid DadosPessoais dadosPessoais, @NotBlank(message = "CRM não pode ser enviado vazio") String crm, Especialidade especialidade) {
}
