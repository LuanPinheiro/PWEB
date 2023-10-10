package br.edu.ifba.medico.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifba.medico.clients.EnderecoClient;
import br.edu.ifba.medico.dtos.MedicoAtualizar;
import br.edu.ifba.medico.dtos.MedicoEnviar;
import br.edu.ifba.medico.dtos.MedicoListar;
import br.edu.ifba.medico.exceptions.InvalidFieldsException;
import br.edu.ifba.medico.exceptions.RegistroNotFoundException;
import br.edu.ifba.medico.models.DadosPessoais;
import br.edu.ifba.medico.models.Especialidade;
import br.edu.ifba.medico.models.Medico;
import br.edu.ifba.medico.repositories.MedicoRepository;

@Service
public class MedicoService implements PessoaServiceInterface<Medico, MedicoEnviar, MedicoListar, MedicoAtualizar>{

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private EnderecoClient enderecoClient;
	
	@Override
	public Page<MedicoListar> listarTodos(Pageable pageable) {
		// Retorna os registros do banco em forma de DTO
		return medicoRepository.findByAtivoTrue(pageable).map(MedicoListar::new);
	}
	
	@Override
	public void novoRegistro(MedicoEnviar dados) {
		// Retorna o endereço que será usado pelo médico
		Long endereco = enderecoClient.gerarEndereco(dados.dadosPessoais().endereco()).getBody();
		Medico medico = new Medico(dados, endereco);
		medico.setAtivo(true);
		medicoRepository.save(medico);
	}

	@Override
	public void removeRegistro(Long id) throws RegistroNotFoundException {
		Medico medico = this.encontrarPorId(id);
		// Apaga o registro logicamente, mudando o valor de ativo
		medico.setAtivo(false);
		medicoRepository.save(medico);
	}

	@Override
	public void atualizaRegistro(MedicoAtualizar dados, Long id) 
			throws RegistroNotFoundException, InvalidFieldsException {
		
		this.validaCamposDto(dados);
		
		Medico medico = encontrarPorId(id);
		
		DadosPessoais dadosPessoais = medico.getDadosPessoais();
		// Altera os valores que foram passados no request body
		dadosPessoais.setNome(dados.nome() == null ? dadosPessoais.getNome() : dados.nome());
		
		// Caso endereço seja passado é necessário um tratamento especial para não gerar tuplas de endereços
		if(dados.endereco() != null)
			dadosPessoais.setEndereco(enderecoClient.gerarEndereco(dados.endereco()).getBody());
		
		dadosPessoais.setTelefone(dados.telefone() == null ? dadosPessoais.getTelefone() : dados.telefone());
		
		medicoRepository.save(medico);
	}
	
	@Override
	public void validaCamposDto(MedicoAtualizar dto) throws InvalidFieldsException {
		if(
				dto.email() != null 
				|| dto.crm() != null
				|| dto.especialidade() != null
				|| dto.allFieldsNull()
				) {
			throw new InvalidFieldsException();
		}
	}
	
	@Override
	public Medico encontrarPorId(Long id) throws RegistroNotFoundException{
		return medicoRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new RegistroNotFoundException("Médico"));
	}

	/**
	 * Verifica se existem médicos com a especialidade indicada e retorna um aleatório
	 * */
	public List<Long> medicosPorEspecialidade(Especialidade especialidade) throws RegistroNotFoundException {
		List<Medico> lista = medicoRepository.findByEspecialidadeAndAtivoTrue(especialidade);
		if(lista.isEmpty()) {
			throw new RegistroNotFoundException("Médico dessa especialidade");
		}
	
		return lista.stream().map((medico) -> medico.getId()).toList();
	}
}
