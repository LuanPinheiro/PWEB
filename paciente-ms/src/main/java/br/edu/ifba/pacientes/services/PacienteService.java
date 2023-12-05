package br.edu.ifba.pacientes.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edu.ifba.pacientes.amqp.DesativacaoDTO;
import br.edu.ifba.pacientes.amqp.Motivo;
import br.edu.ifba.pacientes.clients.EnderecoClient;
import br.edu.ifba.pacientes.clients.EnderecoDTO;
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
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	@Override
	public Page<PacienteListar> listarTodos(Integer page) {
		return pacienteRepository
				.findByAtivoTrue(PageRequest.of(page != null ? page : 0, 10, Sort.by(Sort.Direction.ASC, "dadosPessoais.nome")))
				.map((paciente) -> {
					EnderecoDTO endereco = enderecoClient.encontrarEnderecoPorId(paciente.getDadosPessoais().getEndereco()).getBody();
					return new PacienteListar(paciente, endereco);
				});
	}
	
	public Page<PacienteListar> listarPorEmail(Integer page, String email) {
		return pacienteRepository
				.findByDadosPessoaisEmailAndAtivoTrue(PageRequest.of(page != null ? page : 0, 10, Sort.by(Sort.Direction.ASC, "dadosPessoais.nome")), email)
				.map((paciente) -> {
					EnderecoDTO endereco = enderecoClient.encontrarEnderecoPorId(paciente.getDadosPessoais().getEndereco()).getBody();
					return new PacienteListar(paciente, endereco);
				});
	}

	@Override
	public void novoRegistro(PacienteEnviar dados) throws RegistroExistenteException {
		Long endereco = enderecoClient.gerarEndereco(dados.dadosPessoais().endereco()).getBody().id();
		Paciente paciente = pacienteRepository.findByCpf(dados.cpf()).orElse(new Paciente());
		if(paciente.isAtivo()) {
			throw new RegistroExistenteException();
		}
		paciente.setDadosPessoais(new DadosPessoais(dados.dadosPessoais(), endereco));
		paciente.setCpf(dados.cpf());
		paciente.setAtivo(true);		
		pacienteRepository.save(paciente);
	}

	@Override
	public void removeRegistro(String identificador) throws RegistroNotFoundException {
		Paciente paciente = this.encontrarPorIdentificador(identificador);
		paciente.setAtivo(false);
		pacienteRepository.save(paciente);
		rabbitTemplate.convertAndSend("desativacao_registro_ex","", new DesativacaoDTO(paciente.getCpf(), Motivo.paciente_desativado));
	}

	@Override
	public void atualizaRegistro(PacienteAtualizar dados)
			throws RegistroNotFoundException, InvalidFieldsException {
		
		this.validaCamposDto(dados);
		
		Paciente paciente = encontrarPorIdentificador(dados.cpf());
		
		DadosPessoais dadosPessoais = paciente.getDadosPessoais();
		dadosPessoais.setNome(dados.nome() == null ? dadosPessoais.getNome() : dados.nome());
		dadosPessoais.setTelefone(dados.telefone() == null ? dadosPessoais.getTelefone() : dados.telefone());
		
		if(dados.endereco() != null)
			dadosPessoais.setEndereco(enderecoClient.gerarEndereco(dados.endereco()).getBody().id());
		
		pacienteRepository.save(paciente);
	}
	
	@Override
	public void validaCamposDto(PacienteAtualizar dto) throws InvalidFieldsException {
		if(
				dto.email() != null
				|| dto.allFieldsNull()
				|| (dto.cpf() != null && (dto.telefone() == null && dto.nome() == null))
				) {
			throw new InvalidFieldsException();
		}
	}

	@Override
	public Paciente encontrarPorIdentificador(String identificador) throws RegistroNotFoundException {
		return pacienteRepository.findByCpfAndAtivoTrue(identificador).orElseThrow(() -> new RegistroNotFoundException());
	}
}
