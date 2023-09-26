package br.edu.ifba.trabalho.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
	
	private List<MedicoListar> converteListar(List<Medico> lista){
		return lista.stream().map(MedicoListar::new).collect(Collectors.toList());
	}
	
	public List<MedicoListar> listarTodos() {
		return this.converteListar(medicoRepository.findAll());
	}
	
	public Medico enviarMedico(@RequestBody MedicoEnviar dadosMedico){
		Medico medico = new Medico(dadosMedico);
		
		// Busca se o endereco já existe na base de dados
//		Optional<Endereco> enderecoMedico = enderecoRepository.findAll(dadosMedico.endereco());
		
		
		
		
//		if(enderecoMedico.isEmpty()) {
//			System.out.println("AQUI");
			// Se o endereço não existir cria um novo endereço na base de dados
			// enderecoMedico = enderecoService.enviarEndereco();
//		}
		
		// Se o endereço for validado e criado no banco, altera o endereço do medico
//		if(enderecoMedico.isPresent()) {
//			medico.setEndereco(enderecoMedico.get());	
//		}
				
		medicoRepository.save(medico);
		
		return medico;
	}
}
