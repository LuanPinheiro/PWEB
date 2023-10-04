package br.edu.ifba.trabalho.dtos;

import br.edu.ifba.trabalho.models.Endereco;
import jakarta.validation.constraints.NotBlank;

public record EnderecoEnviar(@NotBlank() Long id, String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep) {
	public EnderecoEnviar(Endereco endereco) {
		this(endereco.getId(), endereco.getLogradouro(), endereco.getNumero(), endereco.getComplemento(), endereco.getBairro(), endereco.getCidade(), endereco.getUf(), endereco.getCep());
	}
}
