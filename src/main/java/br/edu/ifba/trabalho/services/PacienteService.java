package br.edu.ifba.trabalho.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifba.trabalho.dtos.PacienteAtualizar;
import br.edu.ifba.trabalho.dtos.PacienteEnviar;
import br.edu.ifba.trabalho.dtos.PacienteListar;
import br.edu.ifba.trabalho.exceptions.InvalidFieldsException;
import br.edu.ifba.trabalho.exceptions.RegistroNotFoundException;
import br.edu.ifba.trabalho.models.DadosPessoais;
import br.edu.ifba.trabalho.models.Endereco;
import br.edu.ifba.trabalho.models.Paciente;
import br.edu.ifba.trabalho.repositories.PacienteRepository;
import jakarta.validation.Valid;

@Service
public class PacienteService implements PessoaServiceInterface<Paciente, PacienteEnviar, PacienteListar, PacienteAtualizar> {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private EnderecoService enderecoService;
	
	@Override
	public Page<PacienteListar> listarTodos(Pageable pageable) {
		// Retorna os registros do banco em forma de DTO
		return pacienteRepository.findAll(pageable).map(PacienteListar::new);
	}

	@Override
	public void novoRegistro(@Valid PacienteEnviar dados) {
		// Gera nova instância com os dados enviados na requisição e a salva no banco
		Endereco endereco = enderecoService.encontraPorDto(dados.dadosPessoais().endereco());
		Paciente paciente = new Paciente(dados, endereco);
		paciente.setAtivo(true);
		pacienteRepository.save(paciente);
	}

	@Override
	public void removeRegistro(Long id) throws RegistroNotFoundException {
		Paciente paciente = encontrarPorId(id).orElseThrow(() -> new RegistroNotFoundException("Paciente"));
		// Apaga o registro logicamente, mudando o valor de uma variável booleana
		paciente.setAtivo(false);
		pacienteRepository.save(paciente);
	}

	@Override
	public void atualizaRegistro(PacienteAtualizar dados, Long id)
			throws RegistroNotFoundException, InvalidFieldsException {
		
		this.validaCamposDto(dados);
		
		Paciente paciente = encontrarPorId(id).orElseThrow(() -> new RegistroNotFoundException("Paciente"));
		
		// Altera os valores dessa instância no banco, com os dados enviados na requisição e salva no banco
		DadosPessoais dadosPessoais = paciente.getDadosPessoais();
		dadosPessoais.setNome(dados.nome() == null ? dadosPessoais.getNome() : dados.nome());
		dadosPessoais.setTelefone(dados.telefone() == null ? dadosPessoais.getTelefone() : dados.telefone());
		
		if(dados.endereco() != null) {
			Endereco enderecoFinal = enderecoService.encontraPorDto(dados.endereco());
			dadosPessoais.setEndereco(enderecoFinal);
		}
		
		pacienteRepository.save(paciente);
	}
	
	@Override
	public void validaCamposDto(PacienteAtualizar dto) throws InvalidFieldsException {
		if(
				dto.email() != null
				|| dto.cpf() != null
				|| dto.allFieldsNull()
				) {
			throw new InvalidFieldsException();
		}
	}

	@Override
	public Optional<Paciente> encontrarPorId(Long id) throws RegistroNotFoundException {
		return pacienteRepository.findByIdAndAtivoTrue(id);
	}
}
