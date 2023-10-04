package br.edu.ifba.trabalho.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.ifba.trabalho.dtos.MedicoAtualizar;
import br.edu.ifba.trabalho.dtos.MedicoEnviar;
import br.edu.ifba.trabalho.dtos.MedicoListar;
import br.edu.ifba.trabalho.exceptions.RegistroNotFoundException;
import br.edu.ifba.trabalho.models.DadosPessoais;
import br.edu.ifba.trabalho.models.Endereco;
import br.edu.ifba.trabalho.models.Medico;
import br.edu.ifba.trabalho.repositories.MedicoRepository;

@Service
public class MedicoService implements PessoaServiceInterface<Medico, MedicoEnviar, MedicoListar, MedicoAtualizar>{

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Override
	public List<MedicoListar> converteLista(List<Medico> lista){
		// Convertendo cada registro de uma query para um DTO de listagem
		return lista.stream().map(MedicoListar::new).collect(Collectors.toList());
	}
	
	@Override
	public List<MedicoListar> listarTodos(Integer page) {
		// Retorna os registros do banco em forma de DTO
		return this.converteLista(medicoRepository.findAllByAtivoTrueOrderByNomeAsc(PageRequest.of(page == null ? 0 : page, 10)));
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
			throws RegistroNotFoundException {
		
		Medico medico = medicoRepository.findById(id).orElseThrow(RegistroNotFoundException::new);
		
		// Altera os valores dessa instância no banco, com os dados enviados na requisição e salva no banco
		DadosPessoais dadosPessoais = medico.getDadosPessoais();
		dadosPessoais.setNome(dados.nome() == null ? dadosPessoais.getNome() : dados.nome());
		dadosPessoais.setEndereco(dados.endereco() == null ? dadosPessoais.getEndereco() : new Endereco(dados.endereco()));
		dadosPessoais.setTelefone(dados.telefone() == null ? dadosPessoais.getTelefone() : dados.telefone());
		
		medicoRepository.save(medico);
	}
}
