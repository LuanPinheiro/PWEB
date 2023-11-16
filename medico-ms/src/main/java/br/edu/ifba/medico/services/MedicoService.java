package br.edu.ifba.medico.services;


import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edu.ifba.medico.amqp.DesativacaoDTO;
import br.edu.ifba.medico.amqp.Motivo;
import br.edu.ifba.medico.clients.EnderecoClient;
import br.edu.ifba.medico.dtos.MedicoAtualizar;
import br.edu.ifba.medico.dtos.MedicoEnviar;
import br.edu.ifba.medico.dtos.MedicoListar;
import br.edu.ifba.medico.exceptions.InvalidFieldsException;
import br.edu.ifba.medico.exceptions.RegistroExistenteException;
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
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public Page<MedicoListar> listarTodos(Integer page) {
		return medicoRepository
				.findByAtivoTrue(PageRequest.of(page != null ? page : 0, 10, Sort.by(Sort.Direction.ASC, "dadosPessoais.nome")))
				.map(MedicoListar::new);
	}
	
	@Override
	public void novoRegistro(MedicoEnviar dados) throws RegistroExistenteException {
		Long endereco = enderecoClient.gerarEndereco(dados.dadosPessoais().endereco()).getBody();
		Medico medico = medicoRepository.findByCrm(dados.crm()).orElse(new Medico(dados, endereco));
		if(medico.isAtivo()) {
			throw new RegistroExistenteException();
		}
		medico.setAtivo(true);
		medicoRepository.save(medico);
	}

	@Override
	public void removeRegistro(String identificador) throws RegistroNotFoundException {
		System.out.println(identificador);
		Medico medico = this.encontrarPorIdentificador(identificador);
		medico.setAtivo(false);
		medicoRepository.save(medico);
		rabbitTemplate.convertAndSend("desativacao_registro_ex","", new DesativacaoDTO(medico.getId(), Motivo.medico_desativado));
	}

	@Override
	public void atualizaRegistro(MedicoAtualizar dados) 
			throws RegistroNotFoundException, InvalidFieldsException {
		
		this.validaCamposDto(dados);
		
		Medico medico = encontrarPorIdentificador(dados.crm());
		
		DadosPessoais dadosPessoais = medico.getDadosPessoais();
		dadosPessoais.setNome(dados.nome() == null ? dadosPessoais.getNome() : dados.nome());
		
		if(dados.endereco() != null)
			dadosPessoais.setEndereco(enderecoClient.gerarEndereco(dados.endereco()).getBody());
		
		dadosPessoais.setTelefone(dados.telefone() == null ? dadosPessoais.getTelefone() : dados.telefone());
		
		medicoRepository.save(medico);
	}
	
	@Override
	public void validaCamposDto(MedicoAtualizar dto) throws InvalidFieldsException {
		if(
				dto.email() != null 
				|| dto.especialidade() != null
				|| dto.allFieldsNull()
				|| (dto.crm() != null && (dto.telefone() == null && dto.nome() == null))
				) {
			throw new InvalidFieldsException();
		}
		
			
	}
	
	@Override
	public Medico encontrarPorIdentificador(String identificador) throws RegistroNotFoundException{
		return medicoRepository.findByCrmAndAtivoTrue(identificador).orElseThrow(() -> new RegistroNotFoundException("Médico"));
	}

	/**
	 * Verifica se existem médicos com a especialidade indicada e retorna uma lista dos encontrados
	 * */
	public List<Medico> medicosPorEspecialidade(Especialidade especialidade) throws RegistroNotFoundException {
		List<Medico> lista = medicoRepository.findByEspecialidadeAndAtivoTrue(especialidade);
		if(lista.isEmpty()) {
			throw new RegistroNotFoundException("Médico dessa especialidade");
		}
	
		return lista;
	}
}
