package br.edu.ifba.trabalho.services;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifba.trabalho.dtos.ConsultaCancelar;
import br.edu.ifba.trabalho.dtos.ConsultaEnviar;
import br.edu.ifba.trabalho.dtos.ConsultaListar;
import br.edu.ifba.trabalho.exceptions.CantCancelConsultaException;
import br.edu.ifba.trabalho.exceptions.ConsultaExistenteException;
import br.edu.ifba.trabalho.exceptions.ConsultaNotFoundException;
import br.edu.ifba.trabalho.exceptions.DataInvalidaException;
import br.edu.ifba.trabalho.exceptions.MedicoUnavailableException;
import br.edu.ifba.trabalho.exceptions.PacienteJaAgendadoException;
import br.edu.ifba.trabalho.exceptions.RegistroNotFoundException;
import br.edu.ifba.trabalho.models.Consulta;
import br.edu.ifba.trabalho.models.ConsultaId;
import br.edu.ifba.trabalho.models.Medico;
import br.edu.ifba.trabalho.models.Paciente;
import br.edu.ifba.trabalho.repositories.ConsultaRepository;

@Service
public class ConsultaService {

	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private MedicoService medicoService;
	
	@Autowired
	private PacienteService pacienteService;
	
	
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
		
		// Verifica se o médico existe e está ativo
		Medico medico = medicoService.encontrarPorId(dados.idMedico());
		if(medico.getAtivo() == false) {
			throw new RegistroNotFoundException("Médico");
		}
		// Verifica se o paciente existe e está ativo
		Paciente paciente = pacienteService.encontrarPorId(dados.idPaciente());
		if(paciente.getAtivo() == false) {
			throw new RegistroNotFoundException("Paciente");
		}
		
		LocalDate data = dados.data();
		LocalTime hora = dados.hora();
		
		this.validaData(data, hora);
		this.validaConsulta(medico, paciente, data, hora);
		
		consultaRepository.save(new Consulta(medico, paciente, data, hora));
	}
	
	private Consulta encontrarPorIds(ConsultaId ids){
		return consultaRepository.findByIds(ids);
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
				dados.hora()));
		if(consulta == null) {
			System.out.println("Não achou");
			throw new ConsultaNotFoundException("Essa consulta não foi agendada");
		}
		
		if(consulta.isDesmarcado()) {
			throw new ConsultaNotFoundException("Consulta já foi desmarcada");
		}

		// Valida se a consulta está sendo desmarcada com no mínimo 24 horas de antecedência
		LocalDateTime agora = LocalDateTime.now();
		LocalDateTime cancelamento = LocalDateTime.of(dados.data(), dados.hora());
		Duration diff = Duration.between(agora, cancelamento);
		if(diff.toHours() < 24) {
			throw new CantCancelConsultaException();
		}
		
		// Cancela a consulta
		consulta.setDesmarcado(true);
		consulta.setMotivo(dados.motivo());
		consultaRepository.save(consulta);
		
	}
	
	private void validaData(LocalDate data, LocalTime hora) throws DataInvalidaException {
		LocalTime now = LocalTime.now();
		
		
		// Validando se a data é num domingo
		if(
				data.getDayOfWeek() == DayOfWeek.SUNDAY
				|| hora.getHour() < 7
				|| hora.getHour() > 18
				|| hora.getMinute() != 0
				|| hora.getSecond() != 0
				) {
			throw new DataInvalidaException("Clínica não está disponível aos domingos");
		}
		
		// Validando se a data é num domingo ou num horário inválido
				if(
						hora.getHour() < 7
						|| hora.getHour() > 18
						|| hora.getMinute() != 0
						|| hora.getSecond() != 0
						) {
					throw new DataInvalidaException("Clínica não está disponível nesse horário");
				}
		
		
		// Valida se a consulta está sendo feita com no mínimo 30min de antecedência
		Duration diff = Duration.between(now, hora);
		if(diff.toMinutes() <= 30) {
			throw new DataInvalidaException("Consulta só pode ser marcada com no mínimo 30 minutos de antecedência");
		}
	}
	
	private void validaConsulta(Medico medico, Paciente paciente, LocalDate data, LocalTime hora) 
			throws ConsultaExistenteException,
			PacienteJaAgendadoException,
			MedicoUnavailableException {
		// Valida se a consulta já existe
		Consulta consulta = this.encontrarPorIds(new ConsultaId(medico.getId(), paciente.getId(), data, hora));
		if(consulta != null) {
			throw new ConsultaExistenteException();
		}
		
		// Valida se o médico já tem consulta nessa hora
		consulta = consultaRepository.findByIdsMedicoIdAndIdsHora(medico.getId(), hora);
		if(consulta != null) {
			throw new MedicoUnavailableException();
		}
		
		// Valida se o paciente já tem consulta no dia
		consulta = consultaRepository.findByIdsDataAndIdsPacienteId(data, paciente.getId());
		if(consulta != null) {
			throw new PacienteJaAgendadoException();
		}
	}
}
