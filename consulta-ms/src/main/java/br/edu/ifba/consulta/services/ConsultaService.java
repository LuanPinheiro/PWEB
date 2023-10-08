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

import br.edu.ifba.consulta.clients.MedicoClient;
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
import br.edu.ifba.consulta.models.Medico;
import br.edu.ifba.consulta.models.Paciente;
import br.edu.ifba.consulta.repositories.ConsultaRepository;

@Service
public class ConsultaService {

	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private MedicoClient medicoClient;
	
	@Autowired
	private PacienteClient pacienteClient;
	
	
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
		
		Medico medico = dados.idMedico() == null ?
				medicoClient.encontrarPorEspecialidade(dados.especialidade()).getBody()
				// Caso o usuário indique um id de médico
				: medicoClient.encontrarPorId(dados.idMedico()).getBody();
				
		// Verifica se o paciente existe e está ativo
		Paciente paciente = pacienteClient.encontrarPorId(dados.idPaciente()).getBody();
		
		LocalDate data = dados.data();
		LocalTime hora = dados.hora();
		// Valida a data segundo as regras de negócio
		this.validaData(data, hora);
		// Valida a consulta segundo as regras de negócio
		this.validaConsulta(medico, paciente, data, hora);
		
		consultaRepository.save(new Consulta(medico, paciente, data, hora));
	}
	
	public void cancelarConsulta(ConsultaCancelar dados) 
			throws RegistroNotFoundException,
			ConsultaNotFoundException,
			CantCancelConsultaException {
		// Recupera a consulta no banco
		Consulta consulta = this.encontrarPorIds(new ConsultaId(
				dados.idMedico(),
				dados.idPaciente(),
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
		
	}
	
	private void validaData(LocalDate data, LocalTime hora) throws DataInvalidaException {
		LocalTime agora = LocalTime.now();
		
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
		Duration diff = Duration.between(agora, hora);
		if(diff.toMinutes() <= 30)
			throw new DataInvalidaException("Consulta só pode ser marcada com no mínimo 30 minutos de antecedência");
	}
	
	private void validaConsulta(Medico medico, Paciente paciente, LocalDate data, LocalTime hora) 
			throws ConsultaExistenteException,
			PacienteJaAgendadoException,
			MedicoUnavailableException {
		consultaExiste(new ConsultaId(medico.getId(), paciente.getId(), data, hora));
		medicoDisponivel(medico.getId(), hora);
		pacienteTemConsulta(paciente.getId(), data);
	}
	
	/**
	 * Valida se a consulta já existe
	 * */
	private void consultaExiste(ConsultaId ids) throws ConsultaExistenteException{
		if(encontrarPorIds(ids).isPresent()) {
			throw new ConsultaExistenteException();
		}
	}
	
	/**
	 * Encontra uma consulta com os dados da chave primária composta
	 * */
	private Optional<Consulta> encontrarPorIds(ConsultaId ids) {
		return consultaRepository.findByIds(ids);
	}

	/**
	 * Valida se o paciente já tem consulta no dia
	 * */
	private void pacienteTemConsulta(Long id, LocalDate data) throws PacienteJaAgendadoException {
		if(consultaRepository.findByIdsDataAndIdsPacienteId(data, id).isPresent()) {
			throw new PacienteJaAgendadoException();
		}
	}

	/**
	 * Valida se o médico já tem consulta nessa hora
	 * */
	private void medicoDisponivel(Long id, LocalTime hora) throws MedicoUnavailableException {
		if(consultaRepository.findByIdsMedicoIdAndIdsHora(id, hora).isPresent()) {
			throw new MedicoUnavailableException();
		}
	}
}
