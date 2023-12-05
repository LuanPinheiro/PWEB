package br.edu.ifba.pacientes.dtos;

import br.edu.ifba.pacientes.clients.EnderecoDTO;
import br.edu.ifba.pacientes.models.Paciente;

public record PacienteListar(
		String nome,
		String email,
		String cpf,
		String telefone,
		EnderecoDTO endereco) {

	public PacienteListar(Paciente paciente, EnderecoDTO endereco) {
		this(
				paciente.getDadosPessoais().getNome(),
				paciente.getDadosPessoais().getEmail(),
				paciente.getCpf(),
				paciente.getDadosPessoais().getTelefone(),
				endereco
		);
	}
}
