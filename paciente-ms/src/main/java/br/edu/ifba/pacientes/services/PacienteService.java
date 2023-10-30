package br.edu.ifba.pacientes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edu.ifba.pacientes.clients.EnderecoClient;
import br.edu.ifba.pacientes.dtos.PacienteAtualizar;
import br.edu.ifba.pacientes.dtos.PacienteEnviar;
import br.edu.ifba.pacientes.dtos.PacienteListar;
import br.edu.ifba.pacientes.exceptions.InvalidFieldsException;
import br.edu.ifba.pacientes.exceptions.RegistroExistenteException;
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
	public Page<PacienteListar> listarTodos(Integer page) {
		return pacienteRepository
				.findByAtivoTrue(PageRequest.of(page != null ? page : 0, 10, Sort.by(Sort.Direction.ASC, "dadosPessoais.nome")))
				.map(PacienteListar::new);
	}

	@Override
	public void novoRegistro(PacienteEnviar dados) throws RegistroExistenteException {
		Long endereco = enderecoClient.gerarEndereco(dados.dadosPessoais().endereco()).getBody();
		Paciente paciente = pacienteRepository.findByCpf(dados.cpf()).orElse(new Paciente(dados, endereco));
		if(paciente.isAtivo()) {
			throw new RegistroExistenteException();
		}
		paciente.setAtivo(true);
		pacienteRepository.save(paciente);
	}

	@Override
	public void removeRegistro(Long id) throws RegistroNotFoundException {
		Paciente paciente = this.encontrarPorId(id);
		paciente.setAtivo(false);
		pacienteRepository.save(paciente);
	}

	@Override
	public void atualizaRegistro(PacienteAtualizar dados, Long id)
			throws RegistroNotFoundException, InvalidFieldsException {
		
		this.validaCamposDto(dados);
		
		Paciente paciente = encontrarPorId(id);
		
		DadosPessoais dadosPessoais = paciente.getDadosPessoais();
		dadosPessoais.setNome(dados.nome() == null ? dadosPessoais.getNome() : dados.nome());
		dadosPessoais.setTelefone(dados.telefone() == null ? dadosPessoais.getTelefone() : dados.telefone());
		
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
		return pacienteRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new RegistroNotFoundException());
	}
}
