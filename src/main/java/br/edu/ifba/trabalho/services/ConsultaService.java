package br.edu.ifba.trabalho.services;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifba.trabalho.dtos.ConsultaCancelar;
import br.edu.ifba.trabalho.dtos.ConsultaEnviar;
import br.edu.ifba.trabalho.dtos.ConsultaListar;
import br.edu.ifba.trabalho.exceptions.DataInvalidaException;
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
	
	public void marcarConsulta(ConsultaEnviar dados) throws RegistroNotFoundException, DataInvalidaException{
		Calendar data = dados.data();
		this.encontrarPorIds(dados.idMedico(), dados.idPaciente(), dados.data());
		// Domingo == 2
//		if(data.get(Calendar.DAY_OF_WEEK) == 2 
//				|| data.get(Calendar.HOUR_OF_DAY) < 7
//				|| data.get(Calendar.HOUR_OF_DAY) > 18) {
//			throw new DataInvalidaException();
//		}
		
//		 Busca se o médico indicado existe
		Medico medico = medicoService.encontrarPorId(dados.idMedico());;
		
		// Busca se o paciente indicado existe
		Paciente paciente = pacienteService.encontrarPorId(dados.idPaciente());
		// Validar segundo as regras de negócio antes de marcar a consulta
		
		
		
		consultaRepository.save(new Consulta(medico, paciente, data));
	}
	
	private void encontrarPorIds(Long idMedico, Long idPaciente, Calendar data) throws  {
		consultaRepository.findBy(null, null);
	}

	public void cancelarConsulta(ConsultaCancelar dados) throws RegistroNotFoundException {
		// Recupera a consulta no banco
		Consulta consulta = consultaRepository.findByIds(
				new ConsultaId(
					dados.idMedico(),
					dados.idPaciente(),
					dados.data()
				));
		
		// Cancela a consulta
//		Calendar agora = Calendar.getInstance();
		
		consultaRepository.delete(consulta);
	}
	
//	public Consulta encontrarPorIds(ConsultaCancelar dados) throws RegistroNotFoundException{
//		// Busca um registro no banco com os Ids enviado na requisição
//		Optional<Consulta> consulta = consultaRepository
//										.findByIdsAndDataHora(
//															new ConsultaId(
//																	dados.idMedico(),
//																	dados.idPaciente()
//															),
//															dados.data());
//		
//		// Valida se o registro foi encontrado
//		if(consulta.isEmpty()) {
//			throw new RegistroNotFoundException();
//		}
//		return consulta.get();
//	}
}
