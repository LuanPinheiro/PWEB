package br.edu.ifba.consulta.services;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifba.consulta.clients.EmailClient;
import br.edu.ifba.consulta.clients.EmailDto;
import br.edu.ifba.consulta.clients.Especialidade;
import br.edu.ifba.consulta.clients.MedicoClient;
import br.edu.ifba.consulta.clients.MedicoConsulta;
import br.edu.ifba.consulta.clients.PacientaConsulta;
import br.edu.ifba.consulta.clients.PacienteClient;
import br.edu.ifba.consulta.dtos.ConsultaCancelar;
import br.edu.ifba.consulta.dtos.ConsultaEnviar;
import br.edu.ifba.consulta.dtos.ConsultaListar;
import br.edu.ifba.consulta.exceptions.CantCancelConsultaException;
import br.edu.ifba.consulta.exceptions.ConsultaExistenteException;
import br.edu.ifba.consulta.exceptions.ConsultaNotFoundException;
import br.edu.ifba.consulta.exceptions.DataInvalidaException;
import br.edu.ifba.consulta.exceptions.MedicoUnavailableException;
import br.edu.ifba.consulta.exceptions.PacienteJaAgendadoException;
import br.edu.ifba.consulta.exceptions.RegistroNotFoundException;
import br.edu.ifba.consulta.models.Consulta;
import br.edu.ifba.consulta.models.ConsultaId;
import br.edu.ifba.consulta.repositories.ConsultaRepository;

@Service
public class ConsultaService {

	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private MedicoClient medicoClient;
	
	@Autowired
	private PacienteClient pacienteClient;
	
	@Autowired
	private EmailClient emailClient;
	
	public List<ConsultaListar> converteLista(List<Consulta> lista){
		// Convertendo cada registro de uma query para um DTO de listagem
		return lista.stream().map(ConsultaListar::new).collect(Collectors.toList());
	}
	
	public List<ConsultaListar> listarConsultas() {
		// Retorna os registros do banco em forma de DTO
		return this.converteLista(consultaRepository.findAll());
	}
	
	public void marcarConsulta(ConsultaEnviar dados) 
			throws RegistroNotFoundException,
			DataInvalidaException,
			ConsultaExistenteException,
			PacienteJaAgendadoException,
			MedicoUnavailableException{
		
		LocalDate data = dados.data();
		LocalTime hora = dados.hora();
		
		MedicoConsulta medico = validaMedico(dados.idMedico(), dados.especialidade(), data, hora);
				
		PacientaConsulta paciente = pacienteClient.encontrarPorId(dados.idPaciente()).getBody();

		validaData(data, hora);
		Consulta consulta = validaConsulta(medico.id(), paciente.id(), data, hora);
		
		consultaRepository.save(consulta);
		emailClient.sendEmail(new EmailDto(
				"email@gmail.com",
				paciente.email(),
				"Consulta marcada",
				"Olá " + paciente.nome() + 
				"\nSua consulta da especialidade " + medico.especialidade() + 
				" com o médico " + medico.nome() + 
				" foi agendada para o dia " + consulta.getData() + 
				" às " + consulta.getHora()));
		
		emailClient.sendEmail(new EmailDto(
				"email@gmail.com",
				medico.email(),
				"Consulta marcada",
				"Olá " + medico.nome() + 
				"\nUma consulta com o paciente " + paciente.nome() + 
				" foi agendada para o dia " + consulta.getData() + 
				" às " + consulta.getHora()));
	}

	private MedicoConsulta validaMedico(Long idMedico, Especialidade especialidade, LocalDate data, LocalTime hora) throws RegistroNotFoundException {
		if(idMedico == null) {
			List<MedicoConsulta> medicos = medicoClient.encontrarPorEspecialidade(especialidade).getBody();
			return medicoDisponivelLista(medicos, data, hora);
		}
		
		return medicoClient.encontrarPorId(idMedico).getBody();
	}

	public void cancelarConsulta(ConsultaCancelar dados) 
			throws RegistroNotFoundException,
			ConsultaNotFoundException,
			CantCancelConsultaException {
		MedicoConsulta medico = medicoClient.encontrarPorId(dados.idMedico()).getBody();
		
		PacientaConsulta paciente = pacienteClient.encontrarPorId(dados.idPaciente()).getBody();
		
		Consulta consulta = this.encontrarPorIds(new ConsultaId(
				medico.id(),
				paciente.id(),
				dados.data(),
				dados.hora())).orElseThrow(() -> new ConsultaNotFoundException("Essa consulta não foi agendada"));
		
		if(consulta.isDesmarcado()) {
			throw new ConsultaNotFoundException("Consulta já foi desmarcada");
		}

		LocalDateTime agora = LocalDateTime.now();
		LocalDateTime cancelamento = LocalDateTime.of(dados.data(), dados.hora());
		Duration diff = Duration.between(agora, cancelamento);
		// Valida se a consulta está sendo desmarcada com no mínimo 24 horas de antecedência
		if(diff.toHours() < 24) {
			throw new CantCancelConsultaException();
		}
		
		// Cancela a consulta
		consulta.setDesmarcado(true);
		consulta.setMotivo(dados.motivo());
		consultaRepository.save(consulta);
		emailClient.sendEmail(new EmailDto(
				"email@gmail.com",
				paciente.email(),
				"Consulta desmarcada",
				"Olá " + paciente.nome() +
				"\nSua consulta da especialidade " + medico.especialidade() + 
				" com o médico " + medico.nome() + 
				" que estava agendada para o dia " + consulta.getData() + 
				" às " + consulta.getHora() +
				" foi cancelada"));
		
		emailClient.sendEmail(new EmailDto(
				"email@gmail.com",
				medico.email(),
				"Consulta desmarcada",
				"Olá " + medico.nome() +
				"\nA consulta com o paciente " + paciente.nome() + 
				" que estava agendada para o dia " + consulta.getData() + 
				" às " + consulta.getHora() +
				" foi cancelada"));
	}
	
	private void validaData(LocalDate data, LocalTime hora) throws DataInvalidaException {
		LocalDateTime agora = LocalDateTime.now();
		
		// Validando se a data é num domingo
		if(data.getDayOfWeek() == DayOfWeek.SUNDAY)
			throw new DataInvalidaException("Clínica não está disponível aos domingos");
		
		// Validando se a data é num domingo ou num horário inválido
		if(
				hora.getHour() < 7
				|| hora.getHour() > 18
				|| hora.getMinute() != 0
				|| hora.getSecond() != 0
				)
			throw new DataInvalidaException("Clínica não está disponível nesse horário");
		
		
		// Valida se a consulta está sendo feita com no mínimo 30min de antecedência
		if(Duration.between(agora, LocalDateTime.of(data, hora)).toMinutes() <= 30)
			throw new DataInvalidaException("Consulta só pode ser marcada com no mínimo 30 minutos de antecedência");
	}
	
	private Consulta validaConsulta(Long medico, Long paciente, LocalDate data, LocalTime hora) 
			throws ConsultaExistenteException,
			PacienteJaAgendadoException,
			MedicoUnavailableException {
		Consulta consulta = consultaExiste(new ConsultaId(medico, paciente, data, hora));
		medicoDisponivel(medico, data, hora);
		pacienteTemConsulta(paciente, data);
		
		// Caso essa consulta não exista no banco, cria um novo registro
		if(consulta == null) {
			consulta = new Consulta(medico, paciente, data, hora);
		}
		// Se a consulta foi criada agora ou já existia, muda o valor de desmarcado para false, garantindo que ela está marcada
		consulta.setDesmarcado(false);
		return consulta;
	}
	
	/**
	 * Valida se a consulta já existe e a retorna
	 * */
	private Consulta consultaExiste(ConsultaId ids) throws ConsultaExistenteException{
		Optional<Consulta> consulta = encontrarPorIds(ids);
		if(consulta.isPresent() && consulta.get().isDesmarcado() == false) {
			throw new ConsultaExistenteException();
		}
		return consulta.isPresent() ? consulta.get() : null;
	}
	
	/**
	 * Encontra uma consulta com os dados da chave primária composta
	 * */
	private Optional<Consulta> encontrarPorIds(ConsultaId ids) {
		return consultaRepository.findByIdsAndDesmarcadoFalse(ids);
	}

	/**
	 * Valida se o paciente já tem consulta no dia
	 * */
	private void pacienteTemConsulta(Long id, LocalDate data) throws PacienteJaAgendadoException {
		if(consultaRepository.findByIdsDataAndIdsPacienteIdAndDesmarcadoFalse(data, id).isPresent()) {
			throw new PacienteJaAgendadoException();
		}
	}

	/**
	 * Valida se o médico já tem consulta nessa hora
	 * */
	private void medicoDisponivel(Long id, LocalDate data, LocalTime hora) throws MedicoUnavailableException {
		if(consultaRepository.findByIdsMedicoIdAndIdsDataAndIdsHoraAndDesmarcadoFalse(id, data, hora).isPresent()) {
			throw new MedicoUnavailableException();
		}
	}
	
	/**
	 * Valida se algum médico da lista está disponível para esta consulta e retorna 1 deles caso haja
	 * @throws RegistroNotFoundException 
	 * */
	private MedicoConsulta medicoDisponivelLista(List<MedicoConsulta> medicos, LocalDate data, LocalTime hora) throws RegistroNotFoundException {
		MedicoConsulta medicoAvailable = null;
		for (MedicoConsulta medico : medicos) {
			try {
				medicoDisponivel(medico.id(), data,hora);
				medicoAvailable = medico;
				break;
			} catch (MedicoUnavailableException e) {
			}
		}
		if(medicoAvailable == null) {
			throw new RegistroNotFoundException("Médico dessa especialidade para essa data e hora");
		}
		return medicoAvailable;
	}
}
