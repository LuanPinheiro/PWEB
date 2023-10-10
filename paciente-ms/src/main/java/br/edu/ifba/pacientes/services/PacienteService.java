package br.edu.ifba.pacientes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifba.pacientes.clients.EnderecoClient;
import br.edu.ifba.pacientes.dtos.PacienteAtualizar;
import br.edu.ifba.pacientes.dtos.PacienteEnviar;
import br.edu.ifba.pacientes.dtos.PacienteListar;
import br.edu.ifba.pacientes.exceptions.InvalidFieldsException;
import br.edu.ifba.pacientes.exceptions.RegistroNotFoundException;
import br.edu.ifba.pacientes.models.DadosPessoais;
import br.edu.ifba.pacientes.models.Paciente;
import br.edu.ifba.pacientes.repositories.PacienteRepository;

@Service
public class PacienteService implements PessoaServiceInterface<Paciente, PacienteEnviar, PacienteListar, PacienteAtualizar> {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private EnderecoClient enderecoClient;
	
	@Override
	public Page<PacienteListar> listarTodos(Pageable pageable) {
		// Retorna os registros do banco em forma de DTO
		return pacienteRepository.findByAtivoTrue(pageable).map(PacienteListar::new);
	}

	@Override
	public void novoRegistro(PacienteEnviar dados) {
		// Retorna o endereço que será usado pelo paciente
		Long endereco = enderecoClient.gerarEndereco(dados.dadosPessoais().endereco()).getBody();
		Paciente paciente = new Paciente(dados, endereco);
		paciente.setAtivo(true);
		pacienteRepository.save(paciente);
	}

	@Override
	public void removeRegistro(Long id) throws RegistroNotFoundException {
		Paciente paciente = this.encontrarPorId(id);
		// Apaga o registro logicamente, mudando o valor de ativo
		paciente.setAtivo(false);
		pacienteRepository.save(paciente);
	}

	@Override
	public void atualizaRegistro(PacienteAtualizar dados, Long id)
			throws RegistroNotFoundException, InvalidFieldsException {
		
		this.validaCamposDto(dados);
		
		Paciente paciente = encontrarPorId(id);
		
		DadosPessoais dadosPessoais = paciente.getDadosPessoais();
		// Altera os valores que foram passados no request body
		dadosPessoais.setNome(dados.nome() == null ? dadosPessoais.getNome() : dados.nome());
		dadosPessoais.setTelefone(dados.telefone() == null ? dadosPessoais.getTelefone() : dados.telefone());
		
		// Caso endereço seja passado é necessário um tratamento especial para não gerar tuplas de endereços
		if(dados.endereco() != null)
			dadosPessoais.setEndereco(enderecoClient.gerarEndereco(dados.endereco()).getBody());
		
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
	public Paciente encontrarPorId(Long id) throws RegistroNotFoundException {
		return pacienteRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new RegistroNotFoundException("Paciente"));
	}
}
