package br.edu.ifba.medico.dtos;

import br.edu.ifba.clients.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record DadosPessoaisDTO(
		@NotBlank(message = "Campo nome não pode ser vazio") String nome,
		@NotBlank(message = "Campo email não pode ser vazio") String email,
		@NotBlank(message = "Campo telefone não pode ser vazio") String telefone,
		@Valid EnderecoDTO endereco) {
	
}
