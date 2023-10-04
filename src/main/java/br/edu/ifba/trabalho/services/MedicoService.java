package br.edu.ifba.trabalho.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifba.trabalho.dtos.MedicoAtualizar;
import br.edu.ifba.trabalho.dtos.MedicoEnviar;
import br.edu.ifba.trabalho.dtos.MedicoListar;
import br.edu.ifba.trabalho.exceptions.InvalidFieldsException;
import br.edu.ifba.trabalho.exceptions.RegistroNotFoundException;
import br.edu.ifba.trabalho.models.DadosPessoais;
import br.edu.ifba.trabalho.models.Endereco;
import br.edu.ifba.trabalho.models.Medico;
import br.edu.ifba.trabalho.repositories.MedicoRepository;

@Service
public class MedicoService implements PessoaServiceInterface<Medico, MedicoEnviar, MedicoListar, MedicoAtualizar>{

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private EnderecoService enderecoService;
	
	@Override
	public Page<MedicoListar> listarTodos(Pageable pageable) {
		// Retorna os registros do banco em forma de DTO
		return medicoRepository.findAll(pageable).map(MedicoListar::new);
	}
	
	@Override
	public void novoRegistro(MedicoEnviar dados) {
		// Gera nova instância com os dados enviados na requisição e a salva no banco
		Medico medico = new Medico(dados);
		medico.setAtivo(true);
		medicoRepository.save(medico);
	}

	@Override
	public void removeRegistro(Long id) throws RegistroNotFoundException {
		Medico medico = medicoRepository.findById(id).orElseThrow(RegistroNotFoundException::new);
		// Apaga o registro logicamente, mudando o valor de uma variável booleana
		medico.setAtivo(false);
		medicoRepository.save(medico);
	}

	@Override
	public void atualizaRegistro(MedicoAtualizar dados, Long id) 
			throws RegistroNotFoundException, InvalidFieldsException {
		
		this.validaCamposDto(dados);
		
		Medico medico = encontrarPorId(id);
		
		// Altera os valores dessa instância no banco, com os dados enviados na requisição e salva no banco
		DadosPessoais dadosPessoais = medico.getDadosPessoais();
		dadosPessoais.setNome(dados.nome() == null ? dadosPessoais.getNome() : dados.nome());
		
		if(dados.endereco() != null) {
			Endereco enderecoFinal = enderecoService.encontraPorDto(dados.endereco());
			dadosPessoais.setEndereco(enderecoFinal);
		}
		
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
		return medicoRepository.findById(id).orElseThrow(RegistroNotFoundException::new);
	}
}
