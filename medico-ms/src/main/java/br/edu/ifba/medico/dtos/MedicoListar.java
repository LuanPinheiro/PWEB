package br.edu.ifba.medico.dtos;

import br.edu.ifba.medico.clients.EnderecoDTO;
import br.edu.ifba.medico.models.Especialidade;
import br.edu.ifba.medico.models.Medico;

public record MedicoListar(
		String nome,
		String email,
		String crm,
		String telefone,
		EnderecoDTO endereco,
		Especialidade especialidade) {

	public MedicoListar(Medico medico, EnderecoDTO endereco) {
		this(
				medico.getDadosPessoais().getNome(),
				medico.getDadosPessoais().getEmail(),
				medico.getCrm(),
				medico.getDadosPessoais().getTelefone(),
				endereco,
				medico.getEspecialidade());
	}
}
