package br.edu.ifba.trabalho.dtos;

public record PacienteEnviar(String nome, String email, String telefone, String cpf, EnderecoEnviar endereco) {
	public PacienteEnviar() {
		this(null, null, null, null, null);
	}
}
