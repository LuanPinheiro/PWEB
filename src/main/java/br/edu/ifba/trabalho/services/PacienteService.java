package br.edu.ifba.trabalho.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.ifba.trabalho.dtos.PacienteEnviar;
import br.edu.ifba.trabalho.dtos.PacienteListar;
import br.edu.ifba.trabalho.exceptions.InvalidFieldsException;
import br.edu.ifba.trabalho.exceptions.RegistroNotFoundException;
import br.edu.ifba.trabalho.models.Endereco;
import br.edu.ifba.trabalho.models.Paciente;
import br.edu.ifba.trabalho.repositories.PacienteRepository;

@Service
public class PacienteService implements PessoaServiceInterface<Paciente, PacienteEnviar, PacienteListar> {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Override
	public List<PacienteListar> converteLista(List<Paciente> lista) {
		// Convertendo cada registro de uma query para um DTO de listagem
		return lista.stream().map(PacienteListar::new).collect(Collectors.toList());
	}

	@Override
	public List<PacienteListar> listarTodos(Integer page) {
		// Retorna os registros do banco em forma de DTO
		return this.converteLista(pacienteRepository.findAllByAtivoTrueOrderByNomeAsc(PageRequest.of(page == null ? 0 : page, 10)));
	}

	@Override
	public void novoRegistro(PacienteEnviar dados) {
		// Gera nova instância com os dados enviados na requisição e a salva no banco
		Paciente paciente = new Paciente(dados);
		paciente.setAtivo(true);
		pacienteRepository.save(paciente);
	}

	@Override
	public void removeRegistro(Long id) throws RegistroNotFoundException {
		Paciente paciente;
		try {
			paciente = encontrarPorId(id);
		}
		catch(RegistroNotFoundException e) {
			throw e;
		}
		
		// Apaga o registro logicamente, mudando o valor de uma variável booleana
		paciente.setAtivo(false);
		pacienteRepository.save(paciente);
	}

	@Override
	public void atualizaRegistro(PacienteEnviar dados, Long id)
			throws RegistroNotFoundException, InvalidFieldsException {
		// Valida se algum campo inválido foi enviado na requisição
		if(dados.email() != null 
				|| dados.cpf() != null
				|| dados.equals(new PacienteEnviar())) {
			throw new InvalidFieldsException();
		}
		
		Paciente paciente;
		try {
			paciente = encontrarPorId(id);
		}
		catch(RegistroNotFoundException e) {
			throw e;
		}
		
		// Altera os valores dessa instância no banco, com os dados enviados na requisição e salva no banco
		paciente.setNome(dados.nome() == null ? paciente.getNome() : dados.nome());
		paciente.setTelefone(dados.telefone() == null ? paciente.getTelefone() : dados.nome());
		// Mudar o new endereço, precisa identificar se o endereço já existe no banco para não haver tuplas
		paciente.setEndereco(dados.endereco() == null ? paciente.getEndereco() : new Endereco(dados.endereco()));
		
		pacienteRepository.save(paciente);
	}

	@Override
	public Paciente encontrarPorId(Long id) throws RegistroNotFoundException {
		// Busca um registro no banco com o Id enviado na requisição
		Optional<Paciente> paciente = pacienteRepository.findById(id);
		// Valida se o registro foi encontrado
		if(paciente.isEmpty() || paciente.get().getAtivo() == false) {
			throw new RegistroNotFoundException();
		}
		
		return paciente.get();
	}
}
