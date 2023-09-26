package br.edu.ifba.trabalho.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.ifba.trabalho.dtos.MedicoEnviar;
import br.edu.ifba.trabalho.dtos.MedicoListar;
import br.edu.ifba.trabalho.models.Endereco;
import br.edu.ifba.trabalho.models.Medico;
import br.edu.ifba.trabalho.repositories.EnderecoRepository;
import br.edu.ifba.trabalho.repositories.MedicoRepository;

@Service
public class MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	private List<MedicoListar> converteLista(List<Medico> lista){
		return lista.stream().map(MedicoListar::new).collect(Collectors.toList());
	}
	
	public List<MedicoListar> listarTodos(Integer page) {
		if(page == null) {
			page = 0;
		}
		
		return this.converteLista(medicoRepository.findAllByOrderByNomeAsc(PageRequest.of(page, 10)));
	}
	
	public void enviarMedico(MedicoEnviar dadosMedico){
		// Validar endereço antes de continuar...
		Medico medico = new Medico(dadosMedico);
		medicoRepository.save(medico);
	}

	public Boolean removeMedico(Long id) {
		Optional<Medico> medico = medicoRepository.findById(id);
		if(medico.isEmpty()) {
			return false;
		}
		
		medicoRepository.delete(medico.get());
		return true;
	}

	public Boolean atualizaMedico(MedicoEnviar medicoParam, Long id) {
		Optional<Medico> medico = medicoRepository.findById(id);
		if(medico.isEmpty()) {
			return false;
		}
		
		if(medicoParam.email() != null || medicoParam.endereco() != null || medicoParam.especialidade() != null) {
			return false;
		}
		
		medico.get().setNome(medicoParam.nome() == null ? medico.get().getNome() : medicoParam.nome());
		medico.get().setTelefone(medicoParam.telefone());
		medico.get().setEndereco(medicoParam.endereco());
		medicoRepository.save(medico.get());
		return true;
	}
	
}
