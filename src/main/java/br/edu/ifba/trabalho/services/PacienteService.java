package br.edu.ifba.trabalho.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.ifba.trabalho.dtos.PacienteAtualizar;
import br.edu.ifba.trabalho.dtos.PacienteEnviar;
import br.edu.ifba.trabalho.dtos.PacienteListar;
import br.edu.ifba.trabalho.exceptions.RegistroNotFoundException;
import br.edu.ifba.trabalho.models.DadosPessoais;
import br.edu.ifba.trabalho.models.Endereco;
import br.edu.ifba.trabalho.models.Paciente;
import br.edu.ifba.trabalho.repositories.PacienteRepository;

@Service
public class PacienteService implements PessoaServiceInterface<Paciente, PacienteEnviar, PacienteListar, PacienteAtualizar> {

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
		Paciente paciente = pacienteRepository.findById(id).orElseThrow(RegistroNotFoundException::new);
		// Apaga o registro logicamente, mudando o valor de uma variável booleana
		paciente.setAtivo(false);
		pacienteRepository.save(paciente);
	}

	@Override
	public void atualizaRegistro(PacienteAtualizar dados, Long id)
			throws RegistroNotFoundException {
		
		Paciente paciente = pacienteRepository.findById(id).orElseThrow(RegistroNotFoundException::new);
		
		// Altera os valores dessa instância no banco, com os dados enviados na requisição e salva no banco
		DadosPessoais dadosPessoais = paciente.getDadosPessoais();
		dadosPessoais.setNome(dados.nome() == null ? dadosPessoais.getNome() : dados.nome());
		dadosPessoais.setTelefone(dados.telefone() == null ? dadosPessoais.getTelefone() : dados.telefone());
		// Mudar o new endereço, precisa identificar se o endereço já existe no banco para não haver tuplas
		dadosPessoais.setEndereco(dados.endereco() == null ? dadosPessoais.getEndereco() : new Endereco(dados.endereco()));
		
		pacienteRepository.save(paciente);
	}
}
