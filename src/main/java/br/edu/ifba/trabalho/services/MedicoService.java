package br.edu.ifba.trabalho.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.ifba.trabalho.dtos.MedicoEnviar;
import br.edu.ifba.trabalho.dtos.MedicoListar;
import br.edu.ifba.trabalho.exceptions.InvalidFieldsException;
import br.edu.ifba.trabalho.exceptions.RegistroNotFoundException;
import br.edu.ifba.trabalho.models.Endereco;
import br.edu.ifba.trabalho.models.Medico;
import br.edu.ifba.trabalho.repositories.MedicoRepository;

@Service
public class MedicoService implements PessoaServiceInterface<Medico, MedicoEnviar, MedicoListar>{

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
		Medico medico;
		try {
			medico = encontrarPorId(id);
		}
		catch(RegistroNotFoundException e) {
			throw e;
		}
		
		// Apaga o registro logicamente, mudando o valor de uma variável booleana
		medico.setAtivo(false);
		medicoRepository.save(medico);
	}

	@Override
	public void atualizaRegistro(MedicoEnviar dados, Long id) 
			throws RegistroNotFoundException, InvalidFieldsException {
		// Valida se algum campo inválido foi enviado na requisição
		if(dados.email() != null 
				|| dados.crm() != null 
				|| dados.especialidade() != null
				|| dados.equals(new MedicoEnviar())) {
			throw new InvalidFieldsException();
		}
		
		Medico medico;
		try {
			medico = encontrarPorId(id);
		}
		catch(RegistroNotFoundException e) {
			throw e;
		}
		
		// Altera os valores dessa instância no banco, com os dados enviados na requisição e salva no banco
		medico.setNome(dados.nome() == null ? medico.getNome() : dados.nome());
		medico.setTelefone(dados.telefone() == null ? medico.getTelefone() : dados.telefone());
		// Mudar o new endereço, precisa identificar se o endereço já existe no banco para não haver tuplas
		medico.setEndereco(dados.endereco() == null ? medico.getEndereco() : new Endereco(dados.endereco()));
		
		medicoRepository.save(medico);
	}

	@Override
	public Medico encontrarPorId(Long id) throws RegistroNotFoundException{
		// Busca um registro no banco com o Id enviado na requisição
		Optional<Medico> medico = medicoRepository.findById(id);
		// Valida se o registro foi encontrado
		if(medico.isEmpty() || medico.get().getAtivo() == false) {
			throw new RegistroNotFoundException();
		}
		
		return medico.get();
	}
}
