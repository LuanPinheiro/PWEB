package br.edu.ifba.trabalho.dtos;

import br.edu.ifba.trabalho.models.DadosPessoais;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record PacienteEnviar(@Valid DadosPessoais dadosPessoais, @NotBlank(message = "CPF não pode ser enviado vazio") String cpf, @Valid EnderecoEnviar endereco) {
}
